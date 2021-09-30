import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class TaskEPAM {
    private static final String TAG_NAME="name";
    private static final String TAG_HIGH="high";
    private static final String TAG_TREE="tree";
    private static final String TAG_BUSH="bush";
    /**
     * list to save objects from file
     */
    private static List<Plants> plants = new ArrayList<>();
    /**
     * @param args
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        startToParseFile();
        plantGarden((ArrayList<Plants>) plants);
    }

    /**
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * getting started with the xml-file
     * create fabric,file request, create parser
     */
    private static void startToParseFile() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        File file = new File("plants.xml");
        XMLHandler handler = new XMLHandler();
        parser.parse(file, handler);
    }

    /**
     * parsing
     */
    private static class XMLHandler extends DefaultHandler {
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            System.out.println("Start parsing...");
        }
        /**
         * @param uri -element space
         * @param localName - local element name
         * @param qName -element name with the prefix
         * @param attributes
         * get the values from the tags and write them to the object, save them to the list
         */
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
           /*
             parseDouble - return a new double initialized to the value represented by the specified String
            */

            if (qName.equals(TAG_BUSH)) {
                String name = attributes.getValue(TAG_NAME);
                double high = Double.parseDouble(attributes.getValue(TAG_HIGH));
                plants.add(new Bushes(name, high));
            }
            if (qName.equals(TAG_TREE)) {
                String  name = attributes.getValue(TAG_NAME);
                double high = Double.parseDouble(attributes.getValue(TAG_HIGH));
                plants.add(new Trees(name,high));
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
        }
        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
            System.out.println("End parsing.");
        }
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
        }

        @Override
        public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
            super.ignorableWhitespace(ch, start, length);
        }
    }

    /**
     * create abstract class
     */
    public abstract static class Plants {
        private String name;
        private double high;

        public Plants(String name, double high) {
            this.name = name;
            this.high = high;
        }
        public String getName() {
            return name;
        }
        public double getHigh() {
            return high;
        }

    }
    /**
     * create the Bushes sub-class of the Plants class
     */
    public static class Bushes extends Plants {
        public Bushes (String name, double high) {
            super(name, high);
        }
    }
    /**
     * create the Trees sub-class of the Plants class
     */
    public static class Trees extends Plants{
        public Trees(String name, double high) {
            super(name, high);
        }
    }
    /**
     * @param plants - list of objects (Bushes, Trees)
     * counting the number of landings and total height
     */
    public static void plantGarden(ArrayList<Plants> plants){
        int countTrees=0;
        int countBushes=0;
        double highTrees=0.0;
        double highBushes=0.0;

        for (Plants s:plants) {
            if (isaBoolean(s)){
                countTrees++;
                highTrees +=s.high;
            }else {
                countBushes++;
                highBushes +=s.high;
            }
        }
        System.out.println(format("Деревьев посажено: %s, общий рост: %s",countTrees, highTrees));
        System.out.println(format("Кустов посажено: %s, общий рост: %s",countBushes, highBushes));

    }
    /**
     * @param s - object
     * @return boolean: check is-a
     */
    private static boolean isaBoolean(Plants s) {
        return s instanceof Trees;
    }
}

