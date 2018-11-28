import java.util.Random;

class Publication {
    private int viewerID, year;
    private String publication, publisher;

    Publication(int viewerID) {
        this.viewerID = viewerID;
        Random rand = new Random();

        year = rand.nextInt(118) + 1900;

        String[] publishers = {"iBelieve", "The Big Foot Times", "Loch Ness Gazette", "El Chupacabra Monthly",
                "The Abominable Times", "The Literally Unbelievable News"};
        publisher = publishers[rand.nextInt(6)];

        String[] monsters = {"Bigfoot", "El Chupacabra", "Yeti", "Abominable Snowman", "Jersey Devil", "Loch Ness Monster", "Mermaid",
                "Mothman", "Bessie", "Dewey Lake Monster", "Flying Rod", "Giant Anaconda", "Globster", "Honey Island Swamp Monster",
                "Isshii", "The Kraken", "Reptilians", "Jackalope", "Wendigo"};
        String[] adjectives = {"funny", "creepy", "chilling", "shocking", "gory", "colorful", "happy", "surprising", "silly", "real",
                "fake", "horrible", "resourceful", "studious", "sexy", "weird", "outright stupid", "childish", "adult", "illuminating",
                "spiteful", "academic", ""};
        String[] leads = {"look into", "peek into", "story about", "thriller about", "romance about", "op-ed about", "ritual pertaining to",
                "multi-part saga about", "book about", "story of", "story pertaining to", "romance/thriller about", "sneak peek into",
                "first part of a trilogy about", "second installment of a trilogy about", "final installment of a trilogy about"};
        String[] nouns = {"love", "happiness", "blood", "fear", "resentment", "humor", "how babies are made", "when to give up",
                "squirrels", "macaroni", "computer technology", "twigs", "trees", "lakes", "snow", "fire", "big feet", "crunchy granola hippies",
                "cliffs", "the wild west", "Arizona", "the Pacific NorthWest", "New York"};

        publication = monsters[rand.nextInt(monsters.length)] + ": the " + adjectives[rand.nextInt(adjectives.length)]
                + " " + leads[rand.nextInt(leads.length)] + " " + nouns[rand.nextInt(nouns.length)];
    }

    int getViewerID() {
        return viewerID;
    }

    int getYear() {
        return year;
    }

    String getPublication() {
        return publication;
    }

    String getPublisher() {
        return publisher;
    }


}
