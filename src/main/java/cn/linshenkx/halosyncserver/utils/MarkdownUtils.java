package cn.linshenkx.halosyncserver.utils;

import cn.linshenkx.halosyncserver.model.support.HaloConst;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Markdown utils.
 *
 * @author ryanwang
 * @date 2019-06-27
 */
public class MarkdownUtils {

    private static final DataHolder OPTIONS =
            new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(AttributesExtension.create(),
                            AutolinkExtension.create(),
                            EmojiExtension.create(),
                            EscapedCharacterExtension.create(),
                            StrikethroughExtension.create(),
                            TaskListExtension.create(),
                            InsExtension.create(),
                            MediaTagsExtension.create(),
                            TablesExtension.create(),
                            TocExtension.create(),
                            SuperscriptExtension.create(),
                            YamlFrontMatterExtension.create(),
                            GitLabExtension.create()))
                    .set(TocExtension.LEVELS, 255)
                    .set(TablesExtension.WITH_CAPTION, false)
                    .set(TablesExtension.COLUMN_SPANS, false)
                    .set(TablesExtension.MIN_SEPARATOR_DASHES, 1)
                    .set(TablesExtension.MIN_HEADER_ROWS, 1)
                    .set(TablesExtension.MAX_HEADER_ROWS, 1)
                    .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                    .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                    .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
                    .set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.EMOJI_CHEAT_SHEET)
                    .set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.UNICODE_ONLY)
                    .set(HtmlRenderer.SOFT_BREAK, "<br />\n");

    private static final Parser PARSER = Parser.builder(OPTIONS).build();

    private static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
    private static final Pattern FRONT_MATTER = Pattern.compile("^---[\\s\\S]*?---");

    //    /**
    //     * Render html document to markdown document.
    //     *
    //     * @param html html document
    //     * @return markdown document
    //     */
    //    public static String renderMarkdown(String html) {
    //        return FlexmarkHtmlParser.parse(html);
    //    }

    public static String replaceFrontValue(String markdown, String key, String newValue) {
        markdown = markdown.trim();
        Matcher frontMatcher = FRONT_MATTER.matcher(markdown);
        if (!frontMatcher.find()) {
            throw new RuntimeException("该markdown找不到front信息！");
        }
        String front = frontMatcher.group();
        Pattern valuePattern = Pattern.compile(key + ":(.*)");
        Matcher titleMatcher = valuePattern.matcher(front);
        if (!titleMatcher.find()) {
            throw new RuntimeException("该markdown找不到" + key + "信息！");
        }
        String newFront = front.replace(titleMatcher.group(), key + ": " + newValue);
        return markdown.replace(front, newFront);
    }

    public static String getTitle(String markdown) {
        return getFrontValue(markdown, "title");
    }

    public static String getFrontValue(String markdown, String key) {
        Map<String, List<String>> frontMatter = getFrontMatter(markdown);
        List<String> titleList = frontMatter.get(key);
        if (titleList == null) {
            throw new RuntimeException("该markdown找不到" + key + "信息！");
        }
        if (titleList.isEmpty()) {
            return null;
        }
        return titleList.get(0);
    }

    public static List<String> getFrontValueList(String markdown, String key) {
        Map<String, List<String>> frontMatter = getFrontMatter(markdown);
        List<String> titleList = frontMatter.get(key);
        if (titleList == null) {
            throw new RuntimeException("该markdown找不到" + key + "信息！");
        }
        return titleList;
    }

    /**
     * Render Markdown content
     *
     * @param markdown content
     * @return String
     */
    public static String renderHtml(String markdown) {
        if (StringUtils.isBlank(markdown)) {
            return StringUtils.EMPTY;
        }

        // Render netease music short url.
        if (markdown.contains(HaloConst.NETEASE_MUSIC_PREFIX)) {
            markdown = markdown
                    .replaceAll(HaloConst.NETEASE_MUSIC_REG_PATTERN, HaloConst.NETEASE_MUSIC_IFRAME);
        }

        // Render bilibili video short url.
        if (markdown.contains(HaloConst.BILIBILI_VIDEO_PREFIX)) {
            markdown = markdown
                    .replaceAll(HaloConst.BILIBILI_VIDEO_REG_PATTERN, HaloConst.BILIBILI_VIDEO_IFRAME);
        }

        // Render youtube video short url.
        if (markdown.contains(HaloConst.YOUTUBE_VIDEO_PREFIX)) {
            markdown = markdown
                    .replaceAll(HaloConst.YOUTUBE_VIDEO_REG_PATTERN, HaloConst.YOUTUBE_VIDEO_IFRAME);
        }

        Node document = PARSER.parse(markdown);

        return RENDERER.render(document);
    }

    /**
     * Get front-matter
     *
     * @param markdown markdown
     * @return Map
     */
    public static Map<String, List<String>> getFrontMatter(String markdown) {
        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(markdown);
        visitor.visit(document);
        return visitor.getData();
    }

    /**
     * remove front matter
     *
     * @param markdown markdown
     * @return markdown
     */
    public static String removeFrontMatter(String markdown) {
        markdown = markdown.trim();
        Matcher matcher = FRONT_MATTER.matcher(markdown);
        if (matcher.find()) {
            return markdown.replace(matcher.group(), "");
        }
        return markdown;
    }
}
