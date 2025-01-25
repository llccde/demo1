package com.example.demo;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HTMLBuilder {
    ArrayList<SlotAndPortInfo> slots = new ArrayList<>();
    ArrayList<SlotAndPortInfo> ports = new ArrayList<>();
    @Bean
    public HTMLBuilder getBuilder(){
        return new HTMLBuilder();
    }
    public void start(HashMap<String,String> args){
        ArrayList<Document> htmlList = new ArrayList<>();
        for (Map.Entry<String,String> entry : args.entrySet()){
            if(entry.getKey().startsWith("html")){
                htmlList.add(getDocument(entry.getValue()));
            }
        }
        matchSlots(htmlList);
    }
    private Document getDocument(String name){
        File f = new File("E:\\java\\demo\\src\\main\\resources\\static\\component\\"+name);
        Document d;
        try {
            d = Jsoup.parse(f,"UTF-8");
            return d;
        }catch (Exception e){
            e.printStackTrace();
        }
        return Jsoup.parse("<html><p>null</p></html>");

    }
    private void matchSlots(ArrayList<Document> htmlList) {
        for (Document d : htmlList) {
            Elements es = d.select("[slot_type]");
            es.forEach(element -> {
                slots.add(new SlotAndPortInfo(d,PortOrSlot.SLOT,element));
            });

            Elements ep = d.select("[port_type]");
            ep.forEach(element -> {
                ports.add(new SlotAndPortInfo(d,PortOrSlot.PORT,element));
            });

        }

    }
    public void print(){
        System.out.println("SLOTS:\n\n\n");
        for (SlotAndPortInfo slot : slots) {
            System.out.println(slot.toString());
        }
        System.out.println("PORTS:\n\n\n");
        for (SlotAndPortInfo port : ports) {
            System.out.println(port.toString());
        }
    }
    public static void main(String[] args){
        HTMLBuilder hb = new HTMLBuilder();
        HashMap<String,String> arg = new HashMap<>();
        arg.put("html1","content_cpp.html");
        arg.put("html2","smoothscroll_container_with_back_to_top_button.html");
        hb.start(arg);
        hb.print();
    }
}
enum PortOrSlot {
    PORT,
    SLOT
}

class SlotAndPortInfo {

    Document source;
    PortOrSlot type;
    HashMap<String, String> attrs = new HashMap<>();
    Element element;

    public SlotAndPortInfo(Document source, PortOrSlot type, Element element) {
        this.source = source;
        this.type = type;
        this.element = element;
        this.attrs = extractAttributes(element);
    }

    private HashMap<String, String> extractAttributes(Element element) {
        HashMap<String, String> attributes = new HashMap<>();
        element.attributes().forEach(attr -> attributes.put(attr.getKey(), attr.getValue()));
        return attributes;
    }

    public Document getSource() {
        return source;
    }

    public PortOrSlot getType() {
        return type;
    }

    public HashMap<String, String> getAttrs() {
        return attrs;
    }

    public Element getElement() {
        return element;
    }

    @Override

    public String toString() {
        return "SlotOrPortInfo{" +

                ", type=" + type +
                ", attrs=" + attrs +
                ", element=" + element.tagName() + " " + element.attributes() +
                '}';
    }
}