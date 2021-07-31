import SerachEngin.DocumentModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DocumentModelTest {

    DocumentModel documentModel;

    @Before
    public void setUp(){
        documentModel=new DocumentModel("name","content");
    }

    @Test
    public void getNameTest(){
        assertEquals("name", documentModel.getName());
    }

    @Test
    public void getContentTest(){
        assertEquals("content", documentModel.getContent());
    }

    @Test
    public void setNameTest(){
        documentModel.setName("new name");
        assertEquals("new name", documentModel.getName());
    }

    @Test
    public void setContentTest(){
        documentModel.setContent("new content");
        assertEquals("new content", documentModel.getContent());
    }

}
