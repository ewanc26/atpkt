package uk.ewancroft.atpmc.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*
import uk.ewancroft.atpmc.lexicon.registry.LexiconRegistry

/**
 * AST-driven generator using KotlinPoet to generate type-safe Kotlin data classes.
 */
object LexiconGenerator {

    fun generateKotlinClass(lexiconId: String): FileSpec? {
        val schema = LexiconRegistry.get(lexiconId) ?: return null

        val defs = schema["defs"]?.jsonObject
        val main = defs?.get("main")?.jsonObject
        val record = main?.get("record")?.jsonObject ?: return null

        val className = lexiconId.split(".").last().replaceFirstChar { it.uppercase() }
        val packageName = "uk.ewancroft.atpmc.generated"

        val generatedTypes = mutableListOf<TypeSpec>()
        val rootType = generateTypeSpec(className, record, generatedTypes)

        val fileBuilder = FileSpec.builder(packageName, className)
            .addType(rootType)

        generatedTypes.forEach { fileBuilder.addType(it) }

        return fileBuilder.build()
    }

    private fun generateTypeSpec(name: String, schema: JsonObject, generatedTypes: MutableList<TypeSpec>): TypeSpec {
        val typeBuilder = TypeSpec.classBuilder(name)
            .addModifiers(KModifier.DATA)
            .addAnnotation(Serializable::class)

        val properties = schema["properties"]?.jsonObject ?: return typeBuilder.build()

        typeBuilder.primaryConstructor(FunSpec.constructorBuilder().apply {
            properties.forEach { (key, value) ->
                val type = mapType(key, value.jsonObject, generatedTypes)
                addParameter(key, type)
            }
        }.build())

        properties.forEach { (key, value) ->
            val type = mapType(key, value.jsonObject, generatedTypes)
            typeBuilder.addProperty(PropertySpec.builder(key, type).initializer(key).build())
        }

        return typeBuilder.build()
    }

    private fun mapType(key: String, json: JsonObject, generatedTypes: MutableList<TypeSpec>): TypeName {
        val type = json["type"]?.jsonPrimitive?.content
        return when (type) {
            "string" -> String::class.asTypeName()
            "integer" -> Long::class.asTypeName()
            "boolean" -> Boolean::class.asTypeName()
            "object" -> {
                val nestedName = key.replaceFirstChar { it.uppercase() }
                val nestedType = generateTypeSpec(nestedName, json, generatedTypes)
                generatedTypes.add(nestedType)
                ClassName("uk.ewancroft.atpmc.generated", nestedName)
            }
            "array" -> {
                val items = json["items"]?.jsonObject ?: return List::class.asTypeName().parameterizedBy(JsonElement::class.asTypeName())
                val itemType = mapType(key, items, generatedTypes)
                List::class.asTypeName().parameterizedBy(itemType)
            }
            "union" -> {
                val unionName = key.replaceFirstChar { it.uppercase() } + "Union"
                // Generate a sealed interface for the union
                val unionInterface = TypeSpec.interfaceBuilder(unionName)
                    .addModifiers(KModifier.SEALED)
                    .addAnnotation(Serializable::class)
                    .build()
                generatedTypes.add(unionInterface)
                ClassName("uk.ewancroft.atpmc.generated", unionName)
            }
            else -> JsonElement::class.asTypeName()
        }
    }
}
