package egovframework.sample.web;

import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/processFiles")
public class FileProcessingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String DIRECTORY_PATH = "C:/processingTest";
        File folder = new File(DIRECTORY_PATH);
        File[] listOfFiles = folder.listFiles();

        int totalFiles = listOfFiles.length;
        int processedFiles = 0;

        for (File file : listOfFiles) {
            if (file.isFile()) {
                // 파일 내용을 DB에 저장하는 로직
                saveFileToDatabase(file);

                processedFiles++;

                // 세션 또는 다른 공유 리소스에 진행 상태 저장
                req.getSession().setAttribute("fileProcessingProgress", (processedFiles * 100) / totalFiles);
            }
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write("{\"status\":\"success\", \"message\":\"Files processed successfully\"}");
        out.flush();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int progress = 0;
        if(req.getSession().getAttribute("fileProcessingProgress") != null){
            progress = (int) req.getSession().getAttribute("fileProcessingProgress");
        }

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.write("{\"progress\":" + progress + "}");
        out.flush();
    }

    private void saveFileToDatabase(File file) {
        // 파일을 DB에 저장하는 로직 구현
        try{
            Thread.sleep(3000);
            System.out.println("file complete : "+ file.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
