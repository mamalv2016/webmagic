package com.renjie120.math.htmlprint;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.io.Files;

public class HtmlTag {
	public final static String HEAD = "<head>";
	public final static String HEAD_E = "</head>\n";

	public final static String TR = "<tr>";
	public final static String TR_E = "</tr>\n";

	public final static String TD = "<td>";
	public final static String TD_E = "</td>\n";

	public final static String TH = "<th>";
	public final static String TH_E = "</th>\n";

	public final static String THEAD = "<thead>";
	public final static String THEAD_E = "</thead>\n";

	public final static String TABLE = "<table>";
	public final static String TABLE_E = "</table>\n";

	public static String htmlTitle(String str) {
		return "<head><meta http-equiv=Content-Type content='text/html;charset=utf-8'><title>"
				+ str + "</title></head>";
	}

	public static String table(String style) {
		if (style != null)
			return "<table " + style + " >";
		return TABLE;
	}

	public static String tableCss() {
		List<String> ans;
		try {
			ans = Files.readLines(new File("d:\\tablecss.txt"), Charsets.UTF_8);
			return Joiner.on("").useForNull("")
					.appendTo(new StringBuilder().append(""), ans.iterator())
					.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public static String thead(String[] str) {
		StringBuilder bui = new StringBuilder(TR);
		for (String s : str) {
			bui.append(TH).append(s).append(TH_E);
		}
		bui.append(TR_E);
		return bui.toString();
	}

	public static String tr(String[] str) {
		StringBuilder bui = new StringBuilder(TR);
		for (String s : str) {
			bui.append(TD).append(s).append(TD_E);
		}
		bui.append(TR_E);
		return bui.toString();
	}
}
