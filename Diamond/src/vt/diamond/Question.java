package vt.diamond;

// -------------------------------------------------------------------------
/**
 *  wrapper class for question contains string and line position in file
 *
 *  @author Dan
 *  @version Dec 16, 2013
 */
public class Question
{
    private String content;
    private int lnNum;

    public Question(String content, int lnNum) {
        this.content = content;
        this.lnNum = lnNum;
    }

    public void setQuestion(String question) {
        this.content = question;
    }

    public int getLnNum()
    {
        return lnNum;
    }

    public String getContent()
    {
        return content;
    }

    /**
     * if it is a drink card notify activity so the phone can vibrate
     * @return true if it is a drink card
     */
    public boolean drinkUp() {
        return content.equals("Drink");
    }


}
