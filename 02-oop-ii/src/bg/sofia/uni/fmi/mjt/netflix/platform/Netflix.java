package bg.sofia.uni.fmi.mjt.netflix.platform;

import bg.sofia.uni.fmi.mjt.netflix.account.Account;
import bg.sofia.uni.fmi.mjt.netflix.content.Streamable;
import bg.sofia.uni.fmi.mjt.netflix.content.enums.PgRating;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.UserNotFoundException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.netflix.exceptions.ContentUnavailableException;

public class Netflix implements StreamingService {
    private Account[] accounts;
    private Streamable[] streamableContent;
    private int[] contentViews;

    public Netflix(Account[] accounts, Streamable[] streamableContent) {
        this.accounts = accounts;
        this.streamableContent = streamableContent;
        this.contentViews = new int[this.streamableContent.length];
    }

    public void watch(Account user, String videoContentName) throws ContentUnavailableException {
        if(user == null || videoContentName == null || videoContentName.isBlank()) {
            return;
        }

        /** Checks if the given user exists */
        boolean userFound = false;
        for(Account a : this.accounts) {
            if(a.equals(user)) {
                userFound = true;
            }
        }
        if(!userFound) {
            throw new UserNotFoundException();
        }

        /** Checks if the streamable content exists */
        int showIndex = -1;
        Streamable show = null;
        for(int i = 0; i < this.streamableContent.length; ++i) {
            if(this.streamableContent[i].getTitle().equals(videoContentName)) {
                show = this.streamableContent[i];
                showIndex = i;
            }
        }
        if(show == null) {
            throw new ContentNotFoundException();
        }

        /** Checks if the content is age restricted
         *  and if the user meets the requirements */
        if(((show.getRating() == PgRating.PG13 && !user.over14())) || (show.getRating() == PgRating.NC17 && !user.over18())) {
            throw new ContentUnavailableException();
        }

        this.contentViews[showIndex]++;
    }

    public Streamable findByName(String videoContentName) {
        for(Streamable s : streamableContent) {
            if(s.getTitle().equals(videoContentName)) {
                return s;
            }
        }
        return null;
    }

    public Streamable mostViewed() {
        int mostViews = 0;
        int mostViewsIndex = -1;
        for(int i = 0; i < this.contentViews.length; ++i){
            if(this.contentViews[i] > mostViews) {
                mostViews = this.contentViews[i];
                mostViewsIndex = i;
            }
        }

        return mostViewsIndex == -1 ? null : this.streamableContent[mostViewsIndex];
    }

    public int totalWatchedTimeByUsers() {
        int sum = 0;
        for(int i = 0; i < this.contentViews.length; ++i) {
            sum += this.contentViews[i] * this.streamableContent[i].getDuration();
        }
        return sum;
    }
}
