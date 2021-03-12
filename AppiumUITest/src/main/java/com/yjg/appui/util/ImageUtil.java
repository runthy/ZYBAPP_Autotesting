package com.yjg.appui.util;import javax.imageio.*;import java.awt.image.*;import java.awt.*;//Colorimport java.io.*;public class ImageUtil{    public static void main(String[] args)throws Exception{//        float percent = compare(getData("images/10login.jpg"),getData("images/11login.jpg"));//        System.out.println("相似度"+percent+"%");        System.out.println(compareImg("images/zhihu.png","images/zhihu2.png"));    }    public static int[] getData(String name)throws Exception{        BufferedImage img = ImageIO.read(new File(name));        BufferedImage slt = new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);        slt.getGraphics().drawImage(img,0,0,100,100,null);//        System.out.println(slt.getWidth());//        System.out.println(slt.getHeight());                int[] data = new int[256];        for(int x = 0;x<slt.getWidth();x++){            for(int y = 0;y<slt.getHeight();y++){                int rgb = slt.getRGB(x,y);                Color myColor = new Color(rgb);                int r = myColor.getRed();                int g = myColor.getGreen();                int b = myColor.getBlue();                data[(r+g+b)/3]++;            }        }        return data;    }    public static float compare(int[] s,int[] t){        float result = 0F;        for(int i = 0;i<256;i++){            int abs = Math.abs(s[i]-t[i]);            int max = Math.max(s[i],t[i]);                        result += (1-((float)abs/(max==0?1:max)));                    }        return (result/256)*100;    }    public static boolean compareImg(String srcName,String desName) throws Exception{    	if(compare(getData(srcName), getData(desName))==100f){    		return true;    	}else{    		return false;    	}    }}