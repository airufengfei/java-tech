package code.poitl.chatper4;

import com.deepoove.poi.XWPFTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class TestTemplate {

    public static void main(String[] args) throws IOException {
        String property = System.getProperty("user.dir");
        System.out.println(property);
        XWPFTemplate template =
                XWPFTemplate.compile(property + "/src/main/resources/model/word/template.docx").render(new HashMap<String, Object>() {{
            put("title", "nihao");
            put("a", "a");
        }});

        template.writeAndClose(new FileOutputStream(property + "/src/main/resources/model/word/output.docx"));


    }

}
