package blog.ugurcan.firedroid.auth;

import blog.ugurcan.firedroid.auth.view.FacebookLoginButton;
import blog.ugurcan.firedroid.auth.view.TwitterLoginButton;

/**
 * Created by ugurcan on 31.12.2017.
 */
public interface ILogin extends IFireAuth {

    void withGoogle();

    void withFacebook(FacebookLoginButton fbLoginButton);

    void withTwitter(TwitterLoginButton twitterLoginButton);

}
