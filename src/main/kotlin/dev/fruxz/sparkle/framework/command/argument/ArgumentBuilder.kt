package dev.fruxz.sparkle.framework.command.argument

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException
import com.mojang.brigadier.suggestion.Suggestions
import com.mojang.brigadier.suggestion.SuggestionsBuilder
import dev.fruxz.ascend.extension.forceCast
import dev.fruxz.ascend.tool.smart.generate.producible.Producible
import org.bukkit.command.CommandSender
import java.util.concurrent.CompletableFuture

@JvmInline
value class ArgumentParser<T>(val builder: (reader: StringReader) -> T)

@JvmInline
value class SuggestionProvider(val process: (context: CommandContext<CommandSender>, builder: SuggestionsBuilder) -> CompletableFuture<Suggestions>)

@JvmInline
value class ExampleProvider(val builder: () -> Collection<String>)

data class ArgumentBuilder<T>(
    var parser: ArgumentParser<T>? = null,
    var suggestions: SuggestionProvider = SuggestionProvider { _, _ -> Suggestions.empty() },
    var examples: ExampleProvider = ExampleProvider { emptyList() },
) : Producible<ArgumentType<T>> {

    @Throws(CommandSyntaxException::class)
    fun parse(builder: (reader: StringReader) -> T) {
        this.parser = ArgumentParser(builder)
    }

    fun suggest(builder: (context: CommandContext<CommandSender>, builder: SuggestionsBuilder) -> CompletableFuture<Suggestions>) {
        this.suggestions = SuggestionProvider(builder)
    }

    fun examples(builder: () -> Collection<String>) {
        this.examples = ExampleProvider(builder)
    }

    override fun produce(): ArgumentType<T> {
        assert(parser != null) { "Argument parser must be set" }

        return object : ArgumentType<T> {

            override fun parse(reader: StringReader): T =
                this@ArgumentBuilder.parser!!.builder.invoke(reader)

            override fun <S> listSuggestions(
                context: CommandContext<S>,
                builder: SuggestionsBuilder
            ): CompletableFuture<Suggestions> =
                this@ArgumentBuilder.suggestions.process.invoke(context.forceCast(), builder)

            override fun getExamples(): Collection<String> =
                this@ArgumentBuilder.examples.builder.invoke()

        }
    }

}

fun <T> buildArgument(builder: ArgumentBuilder<T>.() -> Unit): ArgumentType<T> =
    ArgumentBuilder<T>().apply(builder).produce()
