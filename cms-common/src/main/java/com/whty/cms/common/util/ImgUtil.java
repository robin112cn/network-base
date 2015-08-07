package com.whty.cms.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImgUtil {

	/**
	 * base64字符串转化成图片
	 * @param imgStr
	 * @param imgFilePath
	 * @return
	 */
    @SuppressWarnings("restriction")
	public static boolean GenerateImage(String imgStr,String imgFilePath)
    {   //对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try 
        {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片
//            String imgFilePath = "d://222.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);    
            out.write(b);
            out.flush();
            out.close();
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
    
    /**
     * 图片转成字符串
     * @param imgFilePath 文件路径
     * @return
     */
    public  static String GetImageStr(String imgFilePath) {
    	
    	if (imgFilePath == null || imgFilePath == "") {
    		return "";
    	}
    	
    	File file = new File(imgFilePath);
    	if (!file.exists()) {
    		return "";
    	}
    	
    	byte[] data = null;
    	try {
    		InputStream in = new FileInputStream(imgFilePath);
    		data = getByte(in);
    		in.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    		BASE64Encoder encoder = new BASE64Encoder();    		
    		String imageString = encoder.encode(data);
    		return imageString;
      	}

      public static byte[] getByte(InputStream in) {
      	if (in == null) {
      		return null;
      	}
      	int sumSize = 0;
     	List<byte[]> totalBytes = new ArrayList<byte[]>();
      	byte[] buffer = new byte[1024];
      	int length = -1;
      	try {
      		while ((length = in.read(buffer)) != -1) {
      			sumSize += length;
      			byte[] tmp = new byte[length];
      			System.arraycopy(buffer, 0, tmp, 0, length);
      			totalBytes.add(tmp);
      		}
      		byte[] data = new byte[sumSize];
      		int start = 0;
      		for (byte[] tmp : totalBytes) {
      			System.arraycopy(tmp, 0, data, start, tmp.length);
      			start += tmp.length;
      		}
      		return data;
      	} catch (IOException e) {
      		e.printStackTrace();
      	}
      	return null;
      	}


//   public static String getImageString(String imgFilePath){
//       String imageString = null;
//       Bitmap mBitmap=BitmapFactory.decodeFile(imgFilePath);
//           if(mBitmap!=null){
//              Matrix matrix = new Matrix();
//                 int mWidth=mBitmap.getWidth();
//                 int mHeight=mBitmap.getHeight();
//                 float scaleWidth=(float)150/mWidth;
//                 float scaleHeight=(float)150/mHeight;
//                 Log.i("scale", scaleWidth+"++++++++++++"+scaleHeight);
//                 matrix.postScale(scaleWidth, scaleHeight);
//            Bitmap newBitmap=Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);
//              
//           ByteArrayOutputStream out=new ByteArrayOutputStream();
//           newBitmap.compress(CompressFormat.JPEG, 100, out);
//           byte []bytes=out.toByteArray();
//           imageString=Base64.encodeToString(bytes, Base64.DEFAULT);
//           System.out.println(imageString);
//           }
//           return imageString;
//      }

	
}
