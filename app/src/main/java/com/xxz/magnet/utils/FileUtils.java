package com.xxz.magnet.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with Android Studio.
 * <p/>
 * Author:xiaxf
 * <p/>
 * Date:2015/7/20.
 */
public class FileUtils {

    public static final String ENCODING_UTF_8 = "UTF-8";

    /**
     * 从文件路径里获取文件名 ，含文件后缀名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return filePath;

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }

    /**
     * 获取该文件的所属上级文件夹
     *
     * @param filePath
     * @return
     */
    public static String getFolderName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return filePath;

        File file = new File(filePath);
        if (file.exists() && file.isDirectory()){
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }

    /**
     * 获取该文件路径的文件后缀名
     *
     * @param filePath
     * @return
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return filePath;

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1)
            return "";
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1).toLowerCase();
    }

    /**
     * 创建文件夹
     */
    public static boolean makeDirs(String filePath) {
        String folderName = getFolderName(filePath);

        if (TextUtils.isEmpty(folderName))
            return false;

        File folder = new File(folderName);
        if (folder.exists() && folder.isDirectory())
            return true;

        return folder.mkdirs();
    }

    /**
     * 创建文件
     */
    public static boolean makeFile(File file) {
        boolean result = false;
        if (!isFileExist(file)) {
            try {
                result = file.createNewFile();
            } catch (IOException e) {
            }
        } else {
            result = true;
        }
        return result;
    }


    /**
     * 判断制定文件路径的文件或文件夹是否存在
     *
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 判断文件／文件夹是否存在
     *
     * @param file
     * @return
     */
    public static boolean isFileExist(File file) {
        return file != null && file.exists() && file.length() > 0;
    }

    /**
     * 重命名
     *
     * @param oldPath 原文件路径
     * @param newPath 新文件路径
     *                *
     */
    public static void renameFile(String oldPath, String newPath) {
        File oldFile = new File(oldPath);
        File newFile = new File(newPath);
        if (oldFile.exists())
            oldFile.renameTo(newFile);
    }

    /**
     * 往文件写内容
     *
     * @param filePath 完整绝对路径
     * @param content  内容
     * @param append   是否添加
     * @return
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;

        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }


    /**
     * write file, the string will be written to the begin of the file
     *
     * @param filePath
     * @param content
     * @return
     */
    public static boolean writeFile(String filePath, String content) {
        return writeFile(filePath, content, false);
    }


    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath
     * @param stream
     * @return
     * @see {@link #writeFile(String, InputStream, boolean)}
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);
    }

    /**
     * write file
     *
     * @param filePath the file to be opened for writing.
     * @param stream   the input stream
     * @param append   if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {
        return writeFile(filePath != null ? new File(filePath) : null, stream, append);
    }

    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file
     * @param stream
     * @return
     * @see {@link #writeFile(File, InputStream, boolean)}
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);
    }

    /**
     * write file
     *
     * @param file   the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the end of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            SystemUtils.closeCloseable(o);
            SystemUtils.closeCloseable(stream);
        }
    }

    /**
     * Indicates if this file represents a directory on the underlying file system.
     *
     * @param directoryPath
     * @return
     */
    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    /**
     * 获取临时文件名
     *
     * @param fileame
     * @return
     */
    public static String getTempFileName(String fileame) {
        return "temp_" + fileame;
    }

    /**
     * 删除文件或文件夹
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path))
            return false;
        File file = new File(path);
        if (!file.exists())
            return true;

        if (file.isFile())
            return deleteFileSafely(file);

        for (File f : file.listFiles()) {
            if (!deleteFile(f.getAbsolutePath()))
                return false;
        }
        return deleteFileSafely(file);
    }

    /**
     * 安全删除文件. 解决文件删除后重新创建导致报错的问题：open failed: EBUSY (Device or resource busy)
     * http://stackoverflow.com/questions/11539657/open-failed-ebusy-device-or-resource-busy
     *
     * @param file
     * @return
     */
    public static boolean deleteFileSafely(File file) {
        String tmpPath = file.getAbsolutePath() + System.currentTimeMillis();
        File tmp = new File(tmpPath);
        file.renameTo(tmp);
        return tmp.delete();
    }

    /**
     * 读取文本文件为String
     *
     * @param file
     * @return
     */
    public static String readFileAsString(File file) {
        if (!isFileExist(file))
            return null;

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            return sb.toString();
        } catch (Exception e) {
            // do nothing
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    LogUtils.e(e);
                }
            }
        }

        return null;
    }

    /**
     * 读取文本文件为String
     *
     * @param inputStream
     * @return
     */
    public static String readFileAsString(InputStream inputStream) {

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }

            return sb.toString();
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            if (br != null) {
                try {
                    br.close();

                } catch (IOException e) {
                    LogUtils.e(e);
                }
            }
        }

        return null;
    }


    public static boolean writeSerializeFile(String filename, Context context, Object obj) {
        FileOutputStream ostream = null;
        try {
            ostream = context.openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return false;
        }

        ObjectOutputStream p = null;
        try {
            p = new ObjectOutputStream(ostream);
            p.writeObject(obj);
            p.flush();
        } catch (IOException e) {
            LogUtils.e(e);
            return false;
        } finally {
            SystemUtils.closeCloseable(p);
            SystemUtils.closeCloseable(ostream);
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public static Object readSerializeFile(String filename, Context context) {
        FileInputStream istream = null;
        try {
            istream = context.openFileInput(filename);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
        ObjectInputStream q = null;
        try {
            q = new ObjectInputStream(istream);
            return q.readObject();
        } catch (IOException e) {
            LogUtils.e(e);
        } catch (ClassNotFoundException e) {
            LogUtils.e(e);
        } finally {
            SystemUtils.closeCloseable(q);
            SystemUtils.closeCloseable(istream);
        }
        return null;
    }

    /**
     * get file name from path, not include suffix
     * <p/>
     * <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("c:\\")              =   ""
     *      getFileNameWithoutExtension("c:\\a")             =   "a"
     *      getFileNameWithoutExtension("c:\\a.b")           =   "a"
     *      getFileNameWithoutExtension("c:a.txt\\a")        =   "a"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     *
     * @param filePath
     * @return file name from path, not include suffix
     * @see
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(".");
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));

    }


    /***
     * @param collection 内容
     * @param singleLine 是否一行一行写
     * @param mark       自定义行分隔标记
     * @param path       文件路径
     * @param append     文件是否末尾追加
     * @return
     */
    public static boolean write(Collection<String> collection, boolean singleLine, String mark, String path,
                                boolean append) {
        return write(collection, singleLine, mark, path, append, ENCODING_UTF_8);
    }

    /***
     * @param collection 内容
     * @param singleLine 是否一行一行写
     * @param path       文件路径
     * @param append     文件是否末尾追加
     * @return
     */
    public static boolean write(Collection<String> collection, boolean singleLine, String path, boolean append) {
        return write(collection, singleLine, null, path, append, ENCODING_UTF_8);
    }

    /***
     * @param collection 内容
     * @param singleLine 是否一行一行写
     * @param mark       自定义行分隔标记
     * @param path       文件路径
     * @return
     */
    public static boolean write(Collection<String> collection, boolean singleLine, String mark, String path) {
        return write(collection, singleLine, mark, path, false, ENCODING_UTF_8);
    }

    /***
     * @param collection 内容
     * @param singleLine 是否一行一行写
     * @param path       文件路径
     * @return
     */
    public static boolean write(Collection<String> collection, boolean singleLine, String path) {
        return write(collection, singleLine, null, path, false, ENCODING_UTF_8);
    }

    /***
     * 写出
     *
     * @param collection  内容集合
     * @param singleLine  是否一行一行写
     * @param mark        自定义行分隔标记
     * @param path        文件路径
     * @param append      是否追加
     * @param charsetName 编码
     * @return
     */
    @SuppressWarnings("finally")
    public static boolean write(Collection<String> collection, boolean singleLine, String mark, String path,
                                boolean append, String charsetName) {
        boolean result = false;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            File desFile = new File(path);

            String fileDirPath = path.substring(0, path.lastIndexOf("/"));
            File fileDIr = new File(fileDirPath);
            if (!fileDIr.exists()) {
                fileDIr.mkdir();
            }

            fileOutputStream = new FileOutputStream(desFile, append);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, charsetName);
            bufferedWriter = new BufferedWriter(outputStreamWriter);

            for (String content : collection) {
                if (mark != null) {
                    content += mark;
                }
                if (singleLine) {
                    bufferedWriter.newLine();
                }
                bufferedWriter.write(content);
                bufferedWriter.flush();
            }
            result = true;
        } catch (Exception e) {
            LogUtils.e(e);
        } finally {
            SystemUtils.closeCloseable(bufferedWriter);
            SystemUtils.closeCloseable(outputStreamWriter);
            SystemUtils.closeCloseable(fileOutputStream);
        }
        return result;
    }

    /**
     * 写出
     *
     * @param content     内容为字符串
     * @param singleLine  是否一行一行写
     * @param mark        自定义行分隔标记
     * @param path        文件路径
     * @param append      是否追加
     * @param charsetName 编码格式
     * @return
     */
    public static boolean writeSting(String content, boolean singleLine, String mark, String path, boolean append,
                                     String charsetName) {

        LinkedList<String> contentList = new LinkedList<String>();
        contentList.add(content);

        return write(contentList, singleLine, mark, path, append, charsetName);
    }

    /**
     * 写出
     *
     * @param content    内容
     * @param singleLine 是否一行一行写
     * @param path       文件路径
     * @param append     是否追加
     * @return
     */
    public static boolean writeSting(String content, boolean singleLine, String path, boolean append) {
        return writeSting(content, singleLine, null, path, append, ENCODING_UTF_8);
    }


    /***
     * 根据制定符号标记读取
     *
     * @param source     文件完整路径
     * @param collection 结果集合
     * @param mark       制定字符串标记
     * @return
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static boolean read(String source, Collection<String> collection, String mark){
        @SuppressWarnings("unused")
        boolean result = false;

        File target = new File(source);

        if (!target.isFile() || !target.canRead() || !target.exists()) {
            return false;
        }

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line;
        try {
            fileInputStream = new FileInputStream(target);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                if (!TextUtils.isEmpty(line)) {
                    if (mark != null && line.endsWith(mark)) {
                        line = line.substring(0, line.lastIndexOf(mark));
                    }
                    collection.add(line);
                }
            }
            result = true;
        } catch (Exception e) {
            LogUtils.e(e);

        } finally {
            SystemUtils.closeCloseable(bufferedReader);
            SystemUtils.closeCloseable(inputStreamReader);
            SystemUtils.closeCloseable(fileInputStream);
        }
        return false;
    }

    /**
     * 读取文件
     *
     * @param source     文件完整路径
     * @param collection 结果集合
     * @return
     * @throws IOException
     */
    public static boolean read(String source, Collection<String> collection) throws IOException {
        return read(source, collection, null);
    }

    /**
     * 读取文件
     *
     * @param source 文件所在路径
     * @return 字符串
     * @throws IOException
     */
    @SuppressWarnings("finally")
    public static String read(String source) {
        @SuppressWarnings("unused")
        StringBuffer sb = new StringBuffer();
        File target = new File(source);

        if (!target.isFile() || !target.canRead() || !target.exists()) {
            return "";
        }

        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String line = null;
        try {
            fileInputStream = new FileInputStream(target);
            inputStreamReader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            sb.setLength(0);
            LogUtils.e(e);
        } finally {
            SystemUtils.closeCloseable(bufferedReader);
            SystemUtils.closeCloseable(inputStreamReader);
            SystemUtils.closeCloseable(fileInputStream);
        }
        return sb.toString();
    }

    private static <T> void writeListToFile(List<T> t, String filename, String path, boolean append) {
        File saveFile = new File(path, filename);
        Gson gson = new Gson();
        String json = gson.toJson(t);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(saveFile, append);
            fos.write(json.getBytes(ENCODING_UTF_8));
            fos.write("\n".getBytes(ENCODING_UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将对象转换成JSON写出到本地文件
     *
     * @param t          被写出对象
     * @param fileName   文件名
     * @param fileDir    文件根路径
     * @param singleLine 是否逐行写
     * @param append     是否追加
     * @return
     */
    public static <T> boolean writeObjectListToFile(T t, String fileDir, String fileName, boolean singleLine,
                                                    boolean append) {
        File saveFile = new File(fileDir, fileName);

        Gson gson = new Gson();
        String jsonResult = gson.toJson(t);

        return writeSting(jsonResult, singleLine, saveFile.getAbsolutePath(), append);
    }

    /**
     * 判断存储路径是否有可读并且可写的权限
     *
     * @param path
     * @return edit by yangguoming at 2015-9-9
     * .rwtest这个隐藏文件OTGUSB上面可写，但是显示的文件不能写，故不准，所以修改成写显式文件
     */
    public static boolean isRW(String path) {
        File file = new File(path, "rwtest");
        boolean delete = false;
        boolean write = false;
        boolean read = false;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                write = file.canWrite();
                if (write) {
                    delete = true;
                }
            } else {
                write = file.createNewFile();
            }
            fis = new FileInputStream(file);
            fis.read();
            read = true;
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            if (delete) {
                file.delete();
            }
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return write && read;
    }


    /**
     * 保存字符串
     *
     * @param datas
     * @param outputStream
     * @return
     */
    public static boolean writeString(@NonNull byte[] datas, @NonNull OutputStream outputStream) {
        boolean result = false;
        try {
            outputStream.write(datas);
            result = true;
        } catch (IOException e) {
            result = false;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static void in2out(@NonNull InputStream ins, @NonNull OutputStream os) throws IOException {
        byte[] buff = new byte[1024];
        int len;
        while ((len = ins.read(buff)) != -1) {
            os.write(buff, 0, len);
        }
    }

}
