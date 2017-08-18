package com.core.jdbc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.core.util.CommConstant;
import com.core.util.DesUtil;
import org.springframework.util.DefaultPropertiesPersister;

/**
 * Created by Administrator on 2016/7/20.
 */
public class JdbcUtil extends DefaultPropertiesPersister {
    public void load(Properties props, InputStream is) throws IOException{

        Properties properties = new Properties();
        properties.load(is);

        if ( (properties.get("jdbc.password") != null) ){
        /*这里通过解密算法，得到你的真实密码，然后写入到properties中*/
            String password = null;
            try {
                password = DesUtil.decrypt( properties.getProperty("jdbc.password"), CommConstant.desDBKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            properties.setProperty("jdbc.password" , password);
        }
        if ( (properties.get("jdbc.username") != null) ){
        /*这里通过解密算法，得到你的真实密码，然后写入到properties中*/
            String username = null;
            try {
                username = DesUtil.decrypt( properties.getProperty("jdbc.username"), CommConstant.desDBKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            properties.setProperty("jdbc.username" , username);
        }
        if ( (properties.get("jdbc.url") != null) ){
        /*这里通过解密算法，得到你的真实密码，然后写入到properties中*/
            String url = null;
            try {
                url = DesUtil.decrypt( properties.getProperty("jdbc.url"), CommConstant.desDBKey);
            } catch (Exception e) {
                e.printStackTrace();
            }
            properties.setProperty("jdbc.url" , url);
        }
        OutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            properties.store(outputStream, "");
            is = outStream2InputStream(outputStream);
            super.load(props, is);
        }catch(IOException e) {
            throw e;
        }finally {
            outputStream.close();
        }
    }


    private InputStream outStream2InputStream(OutputStream out){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos = (ByteArrayOutputStream) out ;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
        return swapStream;
    }
}
