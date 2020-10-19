package us.zoom.sdkexample2;

public interface Constants {

	// TODO Change it to your web domain
    String WEB_DOMAIN = "zoom.us";

	// TODO Change it to your APP Key
    String SDK_KEY = "9YTKLV7cFvCEhhsBcYQC6MGCFuRkbLwyKWkQ";
	
	// TODO Change it to your APP Secret
    String SDK_SECRET = "RuWlg5Fvfv30DHS8GW9hVOHOTXTCbF4DsS7U";

	// TODO change it to your user ID
    String USER_ID = "harel.dhananjay@gmail.com";
	
	// TODO change it to your token
    String ZOOM_TOKEN = "Your token from REST API";
	
	// TODO Change it to your exist meeting ID to start meeting
    String MEETING_ID = "liveclass1";

    /**
     * We recommend that, you can generate jwttoken on your own server instead of hardcore in the code.
     * We hardcore it here, just to run the demo.
     *
     * You can generate a jwttoken on the https://jwt.io/
     * with this payload:
     * {
     *     "appKey": "string", // app key
     *     "iat": long, // access token issue timestamp
     *     "exp": long, // access token expire time
     *     "tokenExp": long // token expire time
     * }
     */
    public final static String SDK_JWTTOKEN = "YOUR JWTTOKEN";

}
