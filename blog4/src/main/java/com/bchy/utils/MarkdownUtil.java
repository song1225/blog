package com.bchy.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;


public class MarkdownUtil {

	/**
	 * Markdown格式转换成HTML格式
	 * 
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtml(String markdown) {
		Parser parser = Parser.builder().build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(document);
	}

	/**
	 * 增加扩展（标题锚点，表格生成）
	 * 
	 * @param markdown
	 * @return
	 */
	public static String markdownToHtmlExtensions(String markdown) {
		// 标题生成id
		Set<Extension> headingAnchorExtension = Collections.singleton(HeadingAnchorExtension.create());
		// 转换table为html
		List<Extension> extensions = Arrays.asList(TablesExtension.create());
		Parser parser = Parser.builder().extensions(extensions).build();
		Node document = parser.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().extensions(headingAnchorExtension).extensions(extensions)
				.attributeProviderFactory(new AttributeProviderFactory() {
					@Override
					public AttributeProvider create(AttributeProviderContext context) {
						return new CustomAttributeProvider();
					}
				}).build();
		return renderer.render(document);
	}
	
	/**
	 * 处理标签的属性
	 */
	static class CustomAttributeProvider implements AttributeProvider{

		@Override
		public void setAttributes(Node node, String tagName, Map<String, String> attributes) {
			if(node instanceof Link){
				attributes.put("target", "_blank");
			}
			if(node instanceof TableBlock){
				attributes.put("class", "ui celled table");
			}
		}
		
	}
}
