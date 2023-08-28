package com.bagas;
//	README
//  ========
//
//  Ini adalah contoh aplikasi Instant Messaging sederhana, sebut saja WazzApp.
//  Di setiap skenario telah dituliskan komentar untuk membantu Anda memahami kebutuhan fungsional yang harus diimplementasi.
//
//  Misi Anda adalah melengkapi potongan kode yang disediakan sehingga aplikasi dapat berjalan sesuai kebutuhan.
//
//  OUTPUT YANG DIHARAPKAN:
//
//  Anton bergabung ke semua channel
//  Budi bergabung ke channel Anak Gaul
//  Jumlah anggota Anak Gaul: 2
//  Jumlah anggota Anak Alay: 1
//
//  Anton:Selamat datang Budi
//  Budi:Terima kasih sudah diundang kemari
//  Anton:No problemo
//
//  List channel terurut abjad:
//  Anak Alay
//  Anak Gaul
//  Flat Earth
//
//  List channel terurut jumlah anggota:
//  Anak Gaul(2)
//  Anak Alay(1)
//  Flat Earth(1)
//
//  Daftar channel dimana Anton terdaftar:
//  Anak Alay
//  Anak Gaul
//  Flat Earth
//
//  Daftar channel dimana Budi terdaftar:
//  Anak Gaul
//
//  Citra bergabung ke WazzApp
//  Citra mencari channel yang mengandung kata "Anak" dan bergabung ke channel yang muncul di hasil pencarian
//  Anak Gaul
//  Anak Alay
//
//  Daftar anggota channel Gaul:
//  Anton
//  Budi
//  Citra

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Wazzapp {

    public static void main(String[] args) {
        // Anton dan Budi bergabung ke WazzApp
        Person anton = new Person("Anton");
        Person budi = new Person("Budi");

        // Channel yang tersedia saat ini ada 3
        Channel channelGaul = new Channel("Anak Gaul");
        Channel channelAlay = new Channel("Anak Alay");
        Channel channelFlatEarth = new Channel("Flat Earth");

        System.out.println("Anton bergabung ke semua channel");
        anton.joinChannel(channelAlay);
        anton.joinChannel(channelGaul);
        anton.joinChannel(channelFlatEarth);

        System.out.println("Budi bergabung ke channel Anak Gaul");
        budi.joinChannel(channelGaul);

        // Secara tidak sengaja, Budi join lagi ke channel Anak Gaul.
        // Karena sebelumnya sudah join, maka tidak ada efek samping yang ditimbulkan.
        // Jumlah anggota channel Gaul tetap 2
        budi.joinChannel(channelGaul);

        // Jumlah anggota channel Anak Gaul = 2, sedangkan channel Anak Alay = 1
        System.out.println("Jumlah anggota Anak Gaul: " + channelGaul.getMemberCount());
        System.out.println("Jumlah anggota Anak Alay: " + channelAlay.getMemberCount());

        System.out.println("");

        // Anton dan Budi saling bertukar pesan di channel Gaul

        // Anton mengirim pesan
        channelGaul.addMessage(new Message(anton, "Selamat datang Budi"));
        // Budi membalas
        channelGaul.addMessage(new Message(budi, "Terima kasih sudah diundang kemari"));
        // Anton membalas lagi
        channelGaul.addMessage(new Message(anton, "No problemo"));

        // Tampilkan pesan dalam urutan pesan baru ada dibawah
        for (Message message : channelGaul.getMessages()) {
//            System.out.println(message);
            System.out.println(message.getMessage());
        }

        System.out.println("");

        // Tampilkan semua channel secara alfabetis
        System.out.println("List channel terurut abjad");
        for (Channel availableChannel : Channel.getListByName()) {
            System.out.println(availableChannel.getName());
        }

        System.out.println("");

        // Tampilkan semua channel secara alfabetis
        System.out.println("List channel terurut jumlah anggota:");
        for (Channel availableChannel : Channel.getListByMemberCount()) {
            System.out.println(availableChannel.getName() + "(" + availableChannel.getMemberCount() + ")");
        }

        System.out.println("");

        System.out.println("Daftar channel dimana Anton terdaftar");
        for (Channel channel : anton.getChannels()) {
            System.out.println(channel.getName());
        }

        System.out.println("");

        System.out.println("Daftar channel dimana Budi terdaftar");
        for (Channel channel : budi.getChannels()) {
            System.out.println(channel.getName());
        }

        System.out.println("");

        System.out.println("Citra bergabung ke WazzApp");
        Person citra = new Person("Citra");

        System.out.println("Citra mencari channel yang mengandung kata \"Anak\" dan bergabung ke channel yang muncul di hasil pencarian");
        List<Channel> channelAnak = Channel.findAllByName("Anak");
        for (Channel channel : channelAnak) {
            citra.joinChannel(channel);
            System.out.println(channel.getName());
        }

        System.out.println("");

        System.out.println("Daftar anggota channel Gaul:");
        for (Person member : channelGaul.getMembers()) {
            System.out.println(member.getName());
        }
    }

    static class Message {
        // <YOUR CODE HERE>
        private Person sender;
        private String message;

        public Person getSender() {
            return sender;
        }

        public void setSender(Person sender) {
            this.sender = sender;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Message(Person sender, String message) {
            this.sender = sender;
            this.message = sender.getName()+":"+message;
        }


    }

    static class Person {
        // <YOUR CODE HERE>
        private String name;
        private List<Channel> channels;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Channel> getChannels() {
            return channels;
        }

        public void setChannels(List<Channel> channels) {
            this.channels = channels;
        }

        public Person(String name) {
            this.name = name;
            this.channels = new ArrayList<>();
        }

        // masuk ke channel baru
        public void joinChannel(Channel channel){
            if (!channels.contains(channel)){
                channels.add(channel);
                channel.addMember(this);
            }
        }
    }

    static class Channel {
        // <YOUR CODE HERE>
        private String name;
        private List<Person> members;
        private static List<Channel> channelList = new ArrayList<>();
        private static List<Message> messages = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Person> getMembers() {
            return members;
        }

        public void setMembers(List<Person> members) {
            this.members = members;
        }

        public List<Channel> getChannelList() {
            return channelList;
        }

        public void setChannelList(List<Channel> channelList) {
            this.channelList = channelList;
        }

        public static List<Message> getMessages() {
            return messages;
        }

        public static void setMessages(List<Message> messages) {
            Channel.messages = messages;
        }

        public Channel(String name) {
            this.name = name;
            this.members = new ArrayList<>();
            channelList.add(this);
        }

        // tambah member ke channel
        public void addMember (Person person){
            members.add(person);
        }

        // hitung jumlah member
        public int getMemberCount(){
            return members.size();
        }

        // mengirim pesan
        public void addMessage(Message message){
            messages.add(message);
        }

        // list channel berdasar nama
        public static List<Channel> getListByName() {
            return channelList.stream()
                    .sorted(Comparator.comparing(Channel::getName))
                    .collect(Collectors.toList());
        }

        // list channel dan jumlah member
        public static List<Channel> getListByMemberCount() {
            return channelList.stream()
                    .sorted(Comparator.comparing(Channel::getMemberCount).reversed())
                    .collect(Collectors.toList());
        }

        // cari channel berdasar nama
        public static List<Channel> findAllByName(String keyword) {
            return channelList.stream()
                    .filter(channel -> channel.name.contains(keyword))
                    .collect(Collectors.toList());
        }
    }
}