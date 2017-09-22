// CPEN 221 / Fall 2016 / MP2: HTML Validator
// You should not modify this file!
// You should read the code here to understand how the HtmlTag class is implemented.

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/** An HtmlTag object represents an HTML tag, such as <b> or </table>. */
public class HtmlTag {
    // fields
    private final String element;
    private final boolean isOpenTag;

    /** Constructs an HTML "opening" tag with the given element (e.g. "table").
      * Throws a NullPointerException if element is null.
      * @param element that represents an opening HTML tag such as <p> or <table>
      * effects: creates a new HtmlTag object for this tag
      */
    public HtmlTag(String element) {
        this(element, true);
    }

    /** Constructs an HTML tag with the given element (e.g. "table") and type.
      * Self-closing tags like <br /> are considered to be "opening" tags,
      * and return true from the isOpenTag method.
      * @param element represents an HTML tag such as <p> or </p>
      * @param isOpenTag indicates if the tag in an opening tag (true) or closing tag (false)
      * effects: creates an HtmlTag object for the provided tag
      * @throws a NullPointerException if element is null. */
    public HtmlTag(String element, boolean isOpenTag) {
        this.element = element.toLowerCase();
        this.isOpenTag = isOpenTag;
    }

    /**
     * Clone this HtmlTag and obtain a reference to a new
     * HtmlTag object. This method is useful if one wants
     * to make <em>deep copies</em> of HtmlTags. Use with
     * case because cloning imposes performance and memory
     * overheads.
     *
     *  @returns a reference to an HtmlTag object that is a clone of
     *  this object.
     */
    public HtmlTag clone() {
    		return new HtmlTag(new String(element), isOpenTag);
    }

    /** Returns true if this tag has the same element and type as the given other tag.
     * @param o is the object to compare this HtmlTag to
     * @returns true if this HtmlTag is equal to o and false otherwise
     */
    public boolean equals(Object o) {
        if (o instanceof HtmlTag) {
            HtmlTag other = (HtmlTag) o;
            return element.equals(other.element) && isOpenTag == other.isOpenTag;
        } else {
            return false;
        }
    }

    /** Returns this HTML tag's element, such as "table" or "p".
     * @param none
     * @returns a string representation of the tag represented by this HtmlTag
     *          withot the < > brackets
     */
    public String getElement() {
        return element;
    }

    /** Returns true if this HTML tag is an "opening" (starting) tag and false
      * if it is a closing tag.
      * Self-closing tags like <br /> are considered to be "opening" tags.
      * @param none
      * @returns true if this HtmlTag is an opening tag or a self-closing tag, and false otherwise
      */
    public boolean isOpenTag() {
        return isOpenTag;
    }

    /** Returns true if the given other tag is non-null and matches this tag;
      * that is, if they have the same element but opposite types,
      * such as <body> and </body>.
      * @param other is an HtmlTag to compare this HtmlTag with
      * @returns true if this HtmlTag "matches" the other HtmlTag and false otherwise.
      *          A match occurs when one tage is an opening tag
      *          and the other is a closing tag.
      */
    public boolean matches(HtmlTag other) {
        return other != null && element.equalsIgnoreCase(other.element) && isOpenTag != other.isOpenTag;
    }

    /** Returns true if this tag does not requires a matching closing tag,
      * which is the case for certain elements such as br and img.
      * @param none
      * @returns true if this HtmlTag is self-closing (e.g., <br />) and false otherwise
      */
    public boolean isSelfClosing() {
        return SELF_CLOSING_TAGS.contains(element);
    }

    /** Returns a string representation of this HTML tag, such as "</table>".
     * @param none
     * @returns a string representation of this HtmlTag
                including the < > brackets
     */
    public String toString() {
        return "<" + (isOpenTag ? "" : "/")
        		+ (element.equals("!--") ? "!-- --" : element) + ">";
    }



    // a set of tags that don't need to be matched (self-closing)
    private static final Set<String> SELF_CLOSING_TAGS = new HashSet<String>(
            Arrays.asList("!doctype", "!--", "?xml", "xml", "area", "base",
                          "basefont", "br", "col", "frame", "hr", "img",
                          "input", "link", "meta", "param"));

    // all whitespace characters; used in text parsing
    private static final String WHITESPACE = " \f\n\r\t";

    /** Reads a string such as "<table>" or "</p>" and converts it into an HtmlTag,
      * which is returned.
      * Throws a NullPointerException if tagText is null.
      * @param tagText is a string that represents an HtmlTag
               including < > brackets
      * @return an HtmlTag that represents the tag given as a String
      */
    public static HtmlTag parse(String tagText) {
        tagText = tagText.trim();
        boolean isOpenTag = !tagText.contains("</");
        String element = tagText.replaceAll("[^a-zA-Z!-?]+", "");
        if (element.contains("!--")) {
            element = "!--";  // HTML comments
        }
        return new HtmlTag(element, isOpenTag);
    }

    /**
      * Takes a string and converts it into a list of HTML tokens
      * represented using a list of HtmlTag objects.
      * The input string represents the HTML text.
      * @param text is the input HTML, and text != null
      * @return a list of HtmlTag objects obtained from the input HTML
      */
	// You don't need to call this method in your MP code
    public static LinkedList<HtmlTag> tokenize(String text) {
        StringBuffer buf = new StringBuffer(text);
        LinkedList<HtmlTag> queue = new LinkedList<HtmlTag>();

        while (true) {
            HtmlTag nextTag = nextTag(buf);
            if (nextTag == null) {
                break;
            } else {
                queue.add(nextTag);
            }
        }

        return queue;
    }

    /**
     * This method grabs the next HTML tag from a string buffer and
     * returns an HtmlTag object for the tag found in the buffer
     * @param buf represents HTML text that needs to be processed;
     *        buf is modified in this method when a tag is found
     *        and the tag and associated text are removed from buf.
     *        This effectively advances the processing point.
     * @return an HtmlTag object for the next HTML tag in buf
     */
    // advances to next tag in input;
    // probably not a perfect HTML tag tokenizer, but it will do for this MP
    private static HtmlTag nextTag(StringBuffer buf) {
        int index1 = buf.indexOf("<");
        int index2 = buf.indexOf(">");

        if (index1 >= 0 && index2 > index1) {
            // check for HTML comments: <!-- -->
            if (index1 + 4 <= buf.length() && buf.substring(index1 + 1, index1 + 4).equals("!--")) {
                // a comment; look for closing comment tag -->
                index2 = buf.indexOf("-->", index1 + 4);
                if (index2 < 0) {
                    return null;
                } else {
                    buf.insert(index1 + 4, " ");    // fixes things like <!--hi-->
                    index2 += 3;    // advance to the closing >
                }
            }

            String element = buf.substring(index1 + 1, index2).trim();

            // remove attributes
            for (int i = 0; i < WHITESPACE.length(); i++) {
                int index3 = element.indexOf(WHITESPACE.charAt(i));
                if (index3 >= 0) {
                    element = element.substring(0, index3);
                }
            }

            // determine whether opening or closing tag
            boolean isOpenTag = true;
            if (element.indexOf("/") == 0) {
                isOpenTag = false;
                element = element.substring(1);
            }
            element = element.replaceAll("[^a-zA-Z0-9!-]+", "");

            buf.delete(0, index2 + 1);
            return new HtmlTag(element, isOpenTag);
        } else {
            return null;
        }
    }
}
