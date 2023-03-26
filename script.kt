/*
    Revo Digital - Gabriele Cavallo
    Script to conver a list of stories (title - description) into a Jira-readable CSV to import them. 
    The input file has this format
    ```
    <story title> - <story description>\n
    ```
    It will be converted into a CSV readable from Jira
 */

import java.io.File
import kotlin.system.exitProcess

/**
 * Remove unuseful stuff from the lines
 */
fun clearText(input: String): String = input.replace('\n', ' ').trim()

/**
 * Format the story CSV line
 * the format is: Summary, Assignee, Reporter, Issue Type, Description, Priority
 */
fun formatStory(title: String, description: String): String =
    "\"${clearText(title)}\", , , Story, \"${clearText(description)}\",\n"

fun main(vararg args: String) {
    if (args.size != 2) {
        println("Error, usage is: <inputfile> <outputfile>")
        exitProcess(-1)
    }

    val inputFileName = args[0]
    val outputFileName = args[1]

    println("Revo Digital - Crafted by Gabriele Cavallo")
    println("Simple Jira story converter")
    println("Input from file ${args[0]}")
    println("Output to file $outputFileName")

    val file = File(inputFileName)
    val output = File(outputFileName)
    output.createNewFile()

    val lines = file.readText().split('.')

    // Remove all blank lines
    lines.filter {
        println(it)
        return@filter it.isNotEmpty() && it.isNotBlank() && it.split('.').first().isNotBlank() && it.split('.').first().isNotEmpty()
    }

    // Parse each story
    lines.forEach {
        val tk = it.split('-')
        val title = tk.first().trim()
        val description = tk.last().trim()

        if(title.isNotEmpty()) {
            println("==============================")
            println("Create story $title")
            println("With description $description")
            println("==============================")
            output.appendText(formatStory(title, description))
        }
    }
    
    println("All work done. Goodbye")
}
