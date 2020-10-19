package us.zoom.sdksample.quiz2;

import android.widget.CheckBox;

/**
 * Created by chrishsu on 4/1/18.
 */

public class Questions {

    private String mQuestions[] = {
            "I'm very hungry. I want ________ food.",
            "I Have 1 cake.  I need more. 1 cake is not ________.",
            "Choose the correct sentence.",
            "Can I have a ___________ water, please?",
            "Let's start cooking! _______ open the refrigerator. Then, find the butter.",
            "She has ________ vegetables."
    };

    private String mChoice[][] = {
            {"a couple of","a lot of", "a little bit of", "a few"},
            {"need","some","enough","more"},
            {"How much apple do you want?","How many pizzas do you want?","How many water do you want?"},
            {"slice of","plate of","piece of","glass of"},
            {"Next","First","Then","Second"},
            {"little bit of","a couple of","several of","couple of"}

    };

    private String mImages[] = {
            "iamhungry",
            "cakeisnotenough",
            "howmuchapplespng",
            "cupofwater",
            "letscook",
            "coupleoffruits"

    };

    private String mQuestionType[] = {
            "radiobutton",
            "radiobutton",
            "radiobutton",
            "radiobutton",
            "radiobutton",
            "radiobutton"

    };

    private int mMaxAnswer[] = {
            1,
            1,
            1,
            1,
            1,
            1

    };

    private String mCorrectAnswer[] = {
            "a lot of",
            "enough",
            "How much apple do you want?",
            "glass of",
            "First",
            "a couple of"


    };

    public String getQuestion(int q) {
        String question = mQuestions[q];
        return question;
    }

    public String[] getChoice(int q) {
        String[] choice = mChoice[q];
        return choice;
    }

    public String getImage(int q) {
        String img = mImages[q];
        return img;
    }

    public String getType(int q) {
        String type = mQuestionType[q];
        return type;
    }

    public int getMaxAnswer(int q) {
        int maxanswer = mMaxAnswer[q];
        return maxanswer;
    }
    public int getLength() {
        return mQuestions.length;
    }

    public String getCorrectAnswer(int q) {
        String correctans = mCorrectAnswer[q];
        return correctans;
    }


}
