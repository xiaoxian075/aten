package com.core.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UploadUtil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String savePath = getServletContext().getRealPath("/WEB-INF/upload");
		String tempPath = getServletContext().getRealPath("/WEB-INF/temp");

		File tempFile = new File(tempPath);
		if (!tempFile.exists()) {
			tempFile.mkdir();
		}
		String msg = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(102400);
			factory.setRepository(tempFile);
			ServletFileUpload upload = new ServletFileUpload();
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength,
						int arg2) {
					System.out.println("文件大小为：" + pContentLength + ",当前已处理："
							+ pBytesRead);
				}
			});
			upload.setHeaderEncoding("UTF-8");
			if (ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			upload.setFileSizeMax(1048576L);
			upload.setSizeMax(10485760L);
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list)
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					System.out.println(name + "=" + value);
				} else {
					String fileName = item.getName();
					System.out.println(fileName);
					if ((!fileName.trim().equals("")) && (fileName != null)) {
						fileName = fileName.substring(fileName
								.lastIndexOf("\\") + 1);
						String fileExtName = fileName.substring(fileName
								.lastIndexOf(".") + 1);
						System.out.println("上传的文件的扩展名是：" + fileExtName);
						InputStream in = item.getInputStream();
						String saveFileName = makeFileName(fileName);
						String realSavePath = makePath(saveFileName, savePath);
						FileOutputStream out = new FileOutputStream(
								realSavePath + "\\" + saveFileName);
						byte[] buffer = new byte[1024];
						int len = 0;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						in.close();
						out.close();
						msg = "上传文件成功";
					}
				}
		} catch (FileSizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("msg", "单个文件上传超过最大值");
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
			return;
		} catch (SizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("msg", "总文件上传超过最大值");
			request.getRequestDispatcher("/message.jsp").forward(request,
					response);
			return;
		} catch (Exception e) {
			msg = "文件上传失败";
			e.printStackTrace();
		}
		request.setAttribute("msg", msg);
		request.getRequestDispatcher("/message.jsp").forward(request, response);
	}

	private String makeFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	private String makePath(String filename, String savePath) {
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xF;
		int dir2 = (hashcode & 0xF0) >> 4;

		String dir = savePath + "\\" + dir1 + "\\" + dir2;

		File file = new File(dir);

		if (!file.exists()) {
			file.mkdirs();
		}
		return dir;
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	public static String upLoadFile(MultipartFile file, String path)
			throws IOException {
		String ext = "";
		Format fmt = new SimpleDateFormat("MMddHHmmss");
		String filename = fmt.format(new Date())+String.valueOf(System.nanoTime());
		String oldname = file.getOriginalFilename();
		if ((oldname.isEmpty()) || (oldname == null))
			ext = "";
		else {
			ext = oldname.substring(oldname.indexOf("."));
		}
		String filePath = filename + ext;

		File fileToCreate = new File(path, filePath);
		FileOutputStream os = new FileOutputStream(fileToCreate);
		os.write(file.getBytes());
		os.flush();
		os.close();
		return filePath;
	}
}