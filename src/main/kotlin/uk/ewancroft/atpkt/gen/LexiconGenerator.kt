package uk.ewancroft.atpkt.gen

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.Serializable
import uk.ewancroft.atpkt.lexicon.*
import java.io.File

/**
 * Generator for producing type-safe Kotlin models and API namespaces from Lexicon schemas.
 * Inspired by the 'atproto.blue' (Python) and 'atproto-js' architecture.
 */
class LexiconGenerator(private val outputDir: File) {
    private val jsonSerializable = ClassName("kotlinx.serialization", "Serializable")

    fun generate(schema: LexiconSchema) {
        val packageName = "uk.ewancroft.atpkt.generated.${schema.id.substringBeforeLast(".")}"
        val className = schema.id.substringAfterLast(".").capitalize()

        val fileBuilder = FileSpec.builder(packageName, className)
        
        // Root class for the Lexicon ID
        val typeSpecBuilder = TypeSpec.classBuilder(className)
            .addAnnotation(jsonSerializable)

        schema.defs.forEach { (defName, definition) ->
            when (definition.type) {
                "record", "object" -> {
                    typeSpecBuilder.addType(generateDataClass(defName, definition))
                }
                "query", "procedure" -> {
                    // Generate Request/Response wrappers
                    generateMethodWrappers(fileBuilder, defName, definition)
                }
            }
        }

        fileBuilder.addType(typeSpecBuilder.build())
        fileBuilder.build().writeTo(outputDir)
    }

    private fun generateDataClass(name: String, definition: LexiconDefinition): TypeSpec {
        val builder = TypeSpec.classBuilder(name.capitalize())
            .addAnnotation(jsonSerializable)
            .addModifiers(KModifier.DATA)

        val constructorBuilder = FunSpec.constructorBuilder()

        definition.properties?.forEach { (propName, property) ->
            val type = mapLexiconTypeToKotlin(property)
            val isNullable = !(definition.required?.contains(propName) ?: false)
            
            constructorBuilder.addParameter(
                ParameterSpec.builder(propName, type.copy(nullable = isNullable))
                    .build()
            )
            builder.addProperty(
                PropertySpec.builder(propName, type.copy(nullable = isNullable))
                    .initializer(propName)
                    .build()
            )
        }

        return builder.primaryConstructor(constructorBuilder.build()).build()
    }

    private fun generateMethodWrappers(fileBuilder: FileSpec.Builder, name: String, definition: LexiconDefinition) {
        // Implementation for Query/Procedure parameter and response models
    }

    private fun mapLexiconTypeToKotlin(property: LexiconProperty): TypeName {
        return when (property.type) {
            "string" -> STRING
            "integer" -> INT
            "boolean" -> BOOLEAN
            "array" -> LIST.parameterizedBy(ANY) // Simplified for foundation
            else -> ANY
        }
    }
}
