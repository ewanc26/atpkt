package uk.ewancroft.atpkt.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import uk.ewancroft.atpkt.lexicon.registry.LexiconRegistry

/**
 * AST-driven generator using KotlinPoet to generate type-safe Kotlin data classes.
 */
object LexiconGenerator {

    fun generateKotlinClass(lexiconId: String): FileSpec? {
        val schema = LexiconRegistry.get(lexiconId) ?: return null
        val defs = schema["defs"]?.jsonObject ?: return null

        val packageParts = lexiconId.split(".")
        val className = packageParts.last().replaceFirstChar { it.uppercase() }
        val packageName = "uk.ewancroft.atpkt.generated." + packageParts.dropLast(1).joinToString(".")

        val fileBuilder = FileSpec.builder(packageName, className)
        val generatedTypes = mutableListOf<TypeSpec>()
        val generatedUnions = mutableSetOf<String>()

        defs.forEach { (defKey, defValue) ->
            val defObj = defValue.jsonObject
            val type = defObj["type"]?.jsonPrimitive?.content ?: return@forEach
            
            val typeName = if (defKey == "main") className else defKey.replaceFirstChar { it.uppercase() }
            
            when (type) {
                "record", "object" -> {
                    generatedTypes.add(generateTypeSpec(typeName, defObj, generatedTypes, packageName, generatedUnions))
                }
                "query", "procedure" -> {
                    defObj["input"]?.jsonObject?.let { input ->
                        generatedTypes.add(generateTypeSpec("${typeName}Input", input, generatedTypes, packageName, generatedUnions))
                    }
                    defObj["output"]?.jsonObject?.let { output ->
                        generatedTypes.add(generateTypeSpec("${typeName}Output", output, generatedTypes, packageName, generatedUnions))
                    }
                }
            }
        }

        if (generatedTypes.isEmpty()) return null

        generatedTypes.forEach { fileBuilder.addType(it) }

        return fileBuilder.build()
    }

    private fun generateTypeSpec(
        name: String, 
        schema: JsonObject, 
        generatedTypes: MutableList<TypeSpec>,
        packageName: String,
        generatedUnions: MutableSet<String>
    ): TypeSpec {
        val typeBuilder = TypeSpec.classBuilder(name)
            .addAnnotation(Serializable::class)

        val properties = if (schema["type"]?.jsonPrimitive?.content == "record") {
            schema["record"]?.jsonObject?.get("properties")?.jsonObject
        } else {
            schema["properties"]?.jsonObject
        }
        
        if (properties == null || properties.isEmpty()) {
            // Not a data class if no properties
            return typeBuilder.build()
        }

        typeBuilder.addModifiers(KModifier.DATA)
        val constructorBuilder = FunSpec.constructorBuilder()
        properties.forEach { (key, value) ->
            val sanitizedKey = if (key in KOTLIN_KEYWORDS) "`$key`" else key
            val type = mapType(name, key, value.jsonObject, generatedTypes, packageName, generatedUnions)
            constructorBuilder.addParameter(
                ParameterSpec.builder(sanitizedKey, type)
                    .defaultValue("null")
                    .build()
            )
            typeBuilder.addProperty(
                PropertySpec.builder(sanitizedKey, type)
                    .initializer(sanitizedKey)
                    .build()
            )
        }
        typeBuilder.primaryConstructor(constructorBuilder.build())

        return typeBuilder.build()
    }

    private fun mapType(
        parentName: String,
        key: String, 
        json: JsonObject, 
        generatedTypes: MutableList<TypeSpec>,
        packageName: String,
        generatedUnions: MutableSet<String>
    ): TypeName {
        val type = json["type"]?.jsonPrimitive?.content
        return when (type) {
            "string" -> String::class.asTypeName().copy(nullable = true)
            "integer" -> Long::class.asTypeName().copy(nullable = true)
            "boolean" -> Boolean::class.asTypeName().copy(nullable = true)
            "unknown" -> JsonElement::class.asTypeName().copy(nullable = true)
            "bytes" -> ByteArray::class.asTypeName().copy(nullable = true)
            "cid-link" -> String::class.asTypeName().copy(nullable = true)
            "object" -> {
                val nestedName = parentName + key.replaceFirstChar { it.uppercase() }
                if (generatedTypes.none { it.name == nestedName }) {
                    val nestedType = generateTypeSpec(nestedName, json, generatedTypes, packageName, generatedUnions)
                    generatedTypes.add(nestedType)
                }
                ClassName(packageName, nestedName).copy(nullable = true)
            }
            "array" -> {
                val items = json["items"]?.jsonObject ?: return List::class.asTypeName().parameterizedBy(JsonElement::class.asTypeName()).copy(nullable = true)
                val itemType = mapType(parentName, key, items, generatedTypes, packageName, generatedUnions)
                List::class.asTypeName().parameterizedBy(itemType).copy(nullable = true)
            }
            "ref" -> {
                val ref = json["ref"]?.jsonPrimitive?.content ?: "JsonElement"
                if (ref.startsWith("#")) {
                    val typeName = ref.substring(1).replaceFirstChar { it.uppercase() }
                    ClassName(packageName, typeName).copy(nullable = true)
                } else {
                    val parts = ref.split("#")
                    val refLexId = parts[0]
                    val refDef = parts.getOrNull(1) ?: "main"
                    
                    val refPackageParts = refLexId.split(".")
                    val refClassName = if (refDef == "main") {
                        refPackageParts.last().replaceFirstChar { it.uppercase() }
                    } else {
                        refDef.replaceFirstChar { it.uppercase() }
                    }
                    val refPackageName = "uk.ewancroft.atpkt.generated." + refPackageParts.dropLast(1).joinToString(".")
                    ClassName(refPackageName, refClassName).copy(nullable = true)
                }
            }
            "union" -> {
                val unionName = parentName + key.replaceFirstChar { it.uppercase() } + "Union"
                if (!generatedUnions.contains(unionName)) {
                    val unionInterface = TypeSpec.interfaceBuilder(unionName)
                        .addModifiers(KModifier.SEALED)
                        .addAnnotation(Serializable::class)
                        .build()
                    generatedTypes.add(unionInterface)
                    generatedUnions.add(unionName)
                }
                ClassName(packageName, unionName).copy(nullable = true)
            }
            else -> JsonElement::class.asTypeName().copy(nullable = true)
        }
    }

    private val KOTLIN_KEYWORDS = setOf(
        "package", "as", "as?", "break", "class", "continue", "do", "else", "false", "for", "fun", "if", "in", "!in",
        "interface", "is", "!is", "null", "object", "package", "return", "super", "this", "throw", "true", "try",
        "typealias", "val", "var", "when", "while"
    )
}
