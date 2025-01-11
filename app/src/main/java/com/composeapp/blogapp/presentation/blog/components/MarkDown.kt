package com.composeapp.blogapp.presentation.blog.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composeapp.blogapp.R

// Enum to define different Markdown token types
enum class MarkdownTokenType {
    TEXT, LINK, BLOCKQUOTE, LIST_ITEM, TABLE, BLOCK_CODE, HEADER, HEADER2, HEADER3
}


enum class MarkDownTextType {
    NONE, BOLD, ITALIC, STRIKETHROUGH, INLINE_CODE
}

// Data class to represent a parsed line
data class ParsedLine(
    val type: MarkdownTokenType,
    val content: String,

    )


data class ParsedText(
    val type: MarkDownTextType,
    val content: String
)

class MarkdownParser {

    companion object {
        const val LIST_ITEM_PREFIX = "- "
        const val LIST_ITEM_PREFIX2 = "* "
        const val INLINE_CODE_PREFIX = "`"
        const val BLOCK_QUOTE_PREFIX = "> "
        const val HEADER_PREFIX = "#"
        const val HEADER2_PREFIX = "##"
        const val HEADER3_PREFIX = "###"
        const val BOLD_PREFIX = "**"
        const val ITALIC_PREFIX = "*"
        const val STRIKETHROUGH_PREFIX = "~~"
        const val LINK_PREFIX = "["
        const val LINK_SUFFIX = "]"
        const val TABLE_PREFIX = "|"
        const val BLOCK_CODE_PREFIX = "```"


    }

    fun parseMarkdown(text: String): List<ParsedLine> {
        val lines = text.split("\n")
        val list = mutableListOf<ParsedLine>()
        val code = StringBuilder()
        val table = StringBuilder()
        var inList = false
        var inTable = false
        var inCodeBlock = false
        for (line in lines) {

            when {
                inCodeBlock -> {

                    if (line.trim().endsWith(BLOCK_CODE_PREFIX)) {
                        inCodeBlock = false
                        list.add(ParsedLine(MarkdownTokenType.BLOCK_CODE, code.toString()))
                        code.clear()
                    } else {
                        code.append(line + "\n")
                    }

                }

                line.trim().startsWith(BLOCK_CODE_PREFIX) -> {
                    if (!inCodeBlock) {
                        inCodeBlock = true
                    }
                }


                line.trim().startsWith(BLOCK_QUOTE_PREFIX) -> {
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.BLOCKQUOTE,
                            line.trim().removePrefix(BLOCK_QUOTE_PREFIX).trim()
                        )
                    )
                }

                line.trim().startsWith(LIST_ITEM_PREFIX) -> {
                    if (!inList) {
                        inList = true
                    }
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.LIST_ITEM,
                            line.trim().removePrefix(LIST_ITEM_PREFIX).trim()
                        )
                    )
                }

                line.trim().startsWith(LIST_ITEM_PREFIX2) -> {
                    if (!inList) {
                        inList = true
                    }
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.LIST_ITEM,
                            line.trim().removePrefix(LIST_ITEM_PREFIX2).trim()
                        )
                    )
                }

                line.trim().startsWith(HEADER3_PREFIX) -> {
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.HEADER3,
                            line.trim().removePrefix(HEADER3_PREFIX).trim()
                        )
                    )
                }

                line.trim().startsWith(HEADER2_PREFIX) -> {
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.HEADER2,
                            line.trim().removePrefix(HEADER2_PREFIX).trim()
                        )
                    )
                }


                line.trim().startsWith(HEADER_PREFIX) -> {
                    list.add(
                        ParsedLine(
                            MarkdownTokenType.HEADER,
                            line.trim().removePrefix(HEADER_PREFIX).trim()
                        )
                    )
                }

                line.trim().startsWith(TABLE_PREFIX) -> {
                    if (!inTable) {
                        inTable = true
                    }
                    val content = line.trim()
                    table.append(content + "\n")

                }

                inTable -> {
                    list.add(ParsedLine(MarkdownTokenType.TABLE, table.toString()))
                    table.clear()
                    inTable = false
                }

                line.trim().startsWith(LINK_PREFIX) -> {
                    val content =
                        line.drop(line.indexOf(LINK_SUFFIX) + 1).removePrefix("(").removeSuffix(")")
                            .trim()
                    list.add(ParsedLine(MarkdownTokenType.LINK, content))
                }

                else -> {
                    list.add(ParsedLine(MarkdownTokenType.TEXT, line))
                }
            }

        }

        if (inCodeBlock) {
            list.add(ParsedLine(MarkdownTokenType.BLOCK_CODE, code.toString()))
        }
        if (inTable) {
            list.add(ParsedLine(MarkdownTokenType.TABLE, table.toString()))
        }
        return list
    }

    fun parseTextLine(text: String): List<ParsedText> {
        var element = text.trim()
        val list = mutableListOf<ParsedText>()

        while (element.isNotEmpty()) {
            element = when {
                element.startsWith(BOLD_PREFIX) -> parsePrefix(
                    element,
                    list,
                    MarkDownTextType.BOLD,
                    BOLD_PREFIX
                )

                element.startsWith(ITALIC_PREFIX) -> parsePrefix(
                    element,
                    list,
                    MarkDownTextType.ITALIC,
                    ITALIC_PREFIX
                )

                element.startsWith(STRIKETHROUGH_PREFIX) -> parsePrefix(
                    element,
                    list,
                    MarkDownTextType.STRIKETHROUGH,
                    STRIKETHROUGH_PREFIX
                )

                element.startsWith(INLINE_CODE_PREFIX) -> parsePrefix(
                    element,
                    list,
                    MarkDownTextType.INLINE_CODE,
                    INLINE_CODE_PREFIX
                )

                else -> parseNone(element, list)
            }
        }

        return list
    }

    private fun parsePrefix(
        element: String,
        list: MutableList<ParsedText>,
        type: MarkDownTextType,
        prefix: String
    ): String {
        val startIndex = element.indexOf(prefix)
        if (startIndex == -1) {
            return parseNone(element, list)
        }

        val textBeforePrefix = element.substring(startIndex + prefix.length)
        val endIndex = textBeforePrefix.indexOf(prefix)
        if (endIndex != -1) {
            list.add(ParsedText(type, textBeforePrefix.substring(0, endIndex)))
            return element.drop(prefix.length).substring(endIndex).removePrefix(prefix).trim()
        } else {
            list.add(ParsedText(type, textBeforePrefix))
            return ""
        }
    }

    private fun parseNone(element: String, list: MutableList<ParsedText>): String {
        val markdownPrefixes =
            listOf(BOLD_PREFIX, ITALIC_PREFIX, STRIKETHROUGH_PREFIX, INLINE_CODE_PREFIX)
        val index = element.indexOfAny(markdownPrefixes)
        if (index != -1) {
            list += ParsedText(MarkDownTextType.NONE, element.substring(0, index))
            return element.substring(index)
        } else {
            list += ParsedText(MarkDownTextType.NONE, element)
            return ""
        }
    }

}


@Composable
fun MarkdownViewer(content: String, modifier: Modifier) {
    if (content.isBlank()) {

        val infiniteTransition = rememberInfiniteTransition(label = "")

        val alpha by infiniteTransition.animateFloat(
            initialValue = 0.5f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
        Column {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
                    ),
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .height(20.dp)
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
                    ),
            )
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .width(160.dp)
                    .height(20.dp)
                    .clip(
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = alpha)
                    ),
            )

        }


    } else {
        val lines = MarkdownParser().parseMarkdown(content)
        MarkdownContent(lines, modifier)
    }
}

@Composable
fun MarkdownContent(lines: List<ParsedLine>, modifier: Modifier) {

    Column(modifier = modifier) {
        lines.forEach { line ->
            Box(
                modifier = Modifier.padding(4.dp)
            ) {
                when (line.type) {

                    MarkdownTokenType.TEXT -> MarkDownText(content = line.content)
                    MarkdownTokenType.LINK -> LinkText(content = line.content)
                    MarkdownTokenType.BLOCKQUOTE -> BlockQuote(content = line.content)
                    MarkdownTokenType.LIST_ITEM -> ListItem(content = line.content)
                    MarkdownTokenType.TABLE -> Table(content = line.content)
                    MarkdownTokenType.BLOCK_CODE -> CodeBlock(content = line.content)
                    MarkdownTokenType.HEADER -> Header(content = line.content)
                    MarkdownTokenType.HEADER2 -> Header2(content = line.content)
                    MarkdownTokenType.HEADER3 -> Header3(content = line.content)
                }
            }

        }

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MarkDownText(content: String, style: TextStyle = MaterialTheme.typography.bodyMedium) {
    val elements = MarkdownParser().parseTextLine(content)
    FlowRow(modifier = Modifier.fillMaxWidth(), maxItemsInEachRow = 20) {
        elements.forEach { element ->
    println(element)
            when (element.type) {
                MarkDownTextType.BOLD -> {
                    BoldText(element.content, style)

                }

                MarkDownTextType.NONE -> {
                    NormalText(element.content, style)
                }

                MarkDownTextType.ITALIC -> {
                    ItalicText(element.content, style)
                }

                MarkDownTextType.STRIKETHROUGH -> {
                    StrikethroughText(element.content)
                }

                MarkDownTextType.INLINE_CODE -> {
                    InlineCode(element.content, style)
                }
            }
        }
    }
}

@Composable
fun NormalText(content: String, style: TextStyle) {
    Text(
        content,
        style = style,
        color = Color.White,
        modifier = Modifier
    )

}

@Composable
fun InlineCode(content: String, style: TextStyle) {
    Box(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 1.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.outlineVariant)
            .padding(horizontal = 4.dp)

    ) {
        Text(
            content,
            style = style.copy(fontFamily = FontFamily.Monospace),
            color = Color.White
        )
    }

}


@Composable
fun Header(content: String) {
    MarkDownText(
        content = content, style = MaterialTheme.typography.headlineLarge
    )
}

@Composable
fun Header2(content: String) {
    MarkDownText(content = content, style = MaterialTheme.typography.headlineMedium)
}

@Composable
fun Header3(content: String) {
    MarkDownText(content = content, style = MaterialTheme.typography.headlineSmall)
}


@Composable
fun BoldText(content: String, style: TextStyle) {
    Text(
        "$content ",
        style = style.copy(fontWeight = FontWeight.ExtraBold),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun ItalicText(content: String, style: TextStyle) {
    Text(
        "$content ",
        style = style.copy(fontStyle = FontStyle.Italic),
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
fun StrikethroughText(content: String) {
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.LineThrough),
        color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
    )
    Text(
        text = " ",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(0.9f)
    )
}


@Composable
fun CodeBlock(content: String) {
    val clipboardManager = LocalClipboardManager.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
            .background(
                MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        IconButton(
            onClick = {
                // Copy to clipboard
                clipboardManager.setText(AnnotatedString(content))


            },
            modifier = Modifier
                .align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_content_copy_24),
                contentDescription = "Info",
                tint = MaterialTheme.colorScheme.onSurface
            )

        }
        Text(
            content,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun BlockQuote(content: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onBackground.copy(0.1f),
                shape = MaterialTheme.shapes.small
            )
            .padding(8.dp)
    ) {
        MarkDownText(content = content.removeSuffix("`"))
    }
}

@Composable
fun ListItem(content: String) {
    val items = content.split("\n")
    Column(
        modifier = Modifier
    ) {
        items.forEach {
            Row {
                NormalText(content = "\t -> ", style = MaterialTheme.typography.bodyMedium)
                MarkDownText(content = it)
            }
        }
    }


}

@Composable
fun LinkText(content: String) {
    Text(
        text = content,
        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
        modifier = Modifier.clickable {

        }
    )
}

@Composable
fun Table(content: String) {
    // Assuming the content is in table format with "pipes" separating columns and rows
    val rows = content.split("\n").map { it.trim() }
    Column(
        modifier = Modifier
            .horizontalScroll(rememberScrollState())
            .fillMaxWidth()
            .padding(0.dp)
            .clip(RoundedCornerShape(16.dp))
            .padding(8.dp)
    ) {
        rows.forEach { row ->
            val cols = row.split("|").filter { it.isNotEmpty() }.map { it.trim() }
            Row(modifier = Modifier.fillMaxWidth()) {
                cols.forEach { col ->

                    Row(
                        modifier = Modifier
                            .width(150.dp)
                            .padding(4.dp)
                            .padding(2.dp)
                    ) {
                        MarkDownText(content = col)
                    }
                }
            }
        }
    }
}