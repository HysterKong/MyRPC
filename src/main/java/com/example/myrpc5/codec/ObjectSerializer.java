package com.example.myrpc5.codec;

import java.io.*;

public class ObjectSerializer implements Serializer {

    // 利用java IO 对象 -> 字节数组
    @Override
    public byte[] serialize(Object o) {
        byte[] bytes = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(o);
            outputStream.flush();
            bytes = byteArrayOutputStream.toByteArray();
            outputStream.close();
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    // 字节数组 -> 对象
    @Override
    public Object deserialize(byte[] bytes, int messageType) {
        Object obj = null;
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        try {
            ObjectInputStream inputStream = new ObjectInputStream(byteArrayInputStream);
            obj = inputStream.readObject();
            inputStream.close();
            bytes.clone();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return obj;
    }

    @Override
    public int getType() {
        return 0;
    }
}
