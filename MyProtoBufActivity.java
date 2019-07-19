package com.jni_text.jyl.jyl_performance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tutorial.AddressBookProtos;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.IOException;

/**
 * 2019.7.19
 * protobuf 序列化和反序列的所消耗的时间和空间都比gson和fastjson小
 */
public class MyProtoBufActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_proto_buf);
        //设置数据
        AddressBookProtos.Person.PhoneNumber.Builder phoneNum = AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("10086");
        AddressBookProtos.Person.Builder person =
                AddressBookProtos.Person.newBuilder().setName("张三").setId(1).addPhones(phoneNum);
        AddressBookProtos.Person.Builder person1 =
                AddressBookProtos.Person.newBuilder().setName("李四").setId(1).addPhones(phoneNum);

        AddressBookProtos.AddressBook addressBook =
                AddressBookProtos.AddressBook.newBuilder().addPeople(person).addPeople(person1).build();

        //保存为一个文件  或者 向服务器发送
        //序列化 将对象转化为byte数组
        byte[] bytes = addressBook.toByteArray();

        //反序列化 读到内存 或者 解析从服务器返回的数据
        try {
            AddressBookProtos.AddressBook addressBook1 =
                    AddressBookProtos.AddressBook.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

//    public byte[] getByte() {
//        AddressBookProtos.Person person = AddressBookProtos.Person.newBuilder().setName("hello").build();
//        byte[] body = person.toByteArray();
//        int length = body.length;
//        //java int类型4个字节
//
//        //该方法表示:protobuf 如果来编码这个int 数据需要多少个字节
//        //最大占用5个字节
//        int headLength = CodedOutputStream.computeUInt32SizeNoTag(length);
//        byte[] head = new byte[headLength];
//        //将protobuf编译的length写入到head里面
//        CodedOutputStream cos = CodedOutputStream.newInstance(head);
//        try {
//            cos.writeUInt32NoTag(body.length);
//            cos.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        //包头+包体长度
//        byte[] bytes = new byte[headLength + body.length];
//
//    }
}
