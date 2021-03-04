package com.potemkin.timetracking.tag;

import com.potemkin.timetracking.entities.Activity;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyTag extends TagSupport {
    public static List<Activity> activityList = new ArrayList<>();

    public int doStartTag() throws JspException {
        String str = "" + activityList.size();
        try {
            JspWriter out = pageContext.getOut();
            out.write(str);

        } catch (IOException ex) {
            throw new JspException(ex.getMessage());
        }
        return SKIP_BODY;
    }
}
