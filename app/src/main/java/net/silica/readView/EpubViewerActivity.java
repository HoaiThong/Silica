package net.silica.readView;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import net.bytecoding.epublibrary.core.EpubBook;
import net.bytecoding.epublibrary.core.EpubCommon;
import net.bytecoding.epublibrary.core.TableOfContent;
import net.silica.MainActivity;
import net.silica.R;
import net.silica.model.Book;
import net.silica.model.Content;
import net.silica.readView.adapter.ChapterAdapter;
import net.silica.readView.adapter.RecyclerViewClickListener;
import net.silica.readView.config.ModeUtils;
import net.silica.readView.config.SettingMode;
import net.silica.readView.dao.HTMLParser;
import net.silica.readView.dao.IOFile;
import net.silica.readView.webview.EpubWebView;
import net.silica.readView.webview.OnSwipeTouchListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class EpubViewerActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private EpubWebView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    int width;
    Context mContext;
    private String rootFolder;
    private String fileDownloaderPath;
    private static final String ROOT_FILE = "file://";

    private int presentPage = 0;
    private int columnWidth, columnHeight;
    Book book;
    private SettingMode mode;
    private ModeUtils modeUtils;
    private DrawerLayout mDrawerLayout;
    private FrameLayout mFrameLayout;
    private TextView title_book_tv;
    private NavigationView mNavigationView;


    private TableOfContent mToc;
    private EpubBook epubBook;
    private EpubCommon epubCommon;
    private String nameFileCache = "cache.txt";
    private int mPosition = 0;
    RecyclerView mRecyclerViewChapter;
    ChapterAdapter chapterAdapter;


    private float touchX, touchY;
    private int screenX, screenY;

    private String XNAME = "initepub";
    private String cssFile = "epubair.css";
    private String jsFile = "loadpage.js";
    private String htmlFile = "webviewmain.html";
    private String urlLoader;
    String url = "https://docs.google.com/uc?id=12NwjLUh4LH3XP1DananzdYQZ9Luz-pMu";


    private Content mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_epub_viewer);
        setTitle("");
        rootFolder = getFilesDir() + "/.nomedia";
        mVisible = true;
        mContext = this;
        epubBook = new EpubBook();
        mToc = new TableOfContent();
        hide();
        initScreenXY();

//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            book = new Book();
//            book = (Book) bundle.getSerializable("book");
//        }
//
        init();
        initCSS();
        initWebView();

        final DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute(url);
    }

    public void init() {

        mode = new SettingMode();
        modeUtils = new ModeUtils(EpubViewerActivity.this);
        mode = modeUtils.getSettingMode();
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mFrameLayout = findViewById(R.id.frame_layout);
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);
        mNavigationView = findViewById(R.id.nav_view);
        title_book_tv = findViewById(R.id.tv_nav_title_book);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(drawerToggle);
//        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
//            @Override
//            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
//                if (slideOffset == 0) {
//                    // drawer closed
//
//                    invalidateOptionsMenu();
//                } else if (slideOffset != 0) {
//                    // started opening
//                    invalidateOptionsMenu();
//                }
//            }
//
//            @Override
//            public void onDrawerOpened(@NonNull View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerClosed(@NonNull View drawerView) {
//
//            }
//
//            @Override
//            public void onDrawerStateChanged(int newState) {
//
//            }
//
//        });
    }

    public void initScreenXY() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getRealSize(size);
        screenX = size.x;
        screenY = size.y;

        getWindowManager().getDefaultDisplay().getRealSize(size);
        columnHeight = size.y;
        columnWidth = size.x;
        Log.d("Y", String.valueOf(screenY));
        Log.d("H", String.valueOf(columnHeight));
        Log.d("HS", String.valueOf(size.y));
        Log.d("Cwith1 ", String.valueOf(columnWidth));
    }

    public void initWebView() {
        WebSettings webSetting = mContentView.getSettings();
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        mContentView.clearCache(true);
        mContentView.setHorizontalScrollBarEnabled(false);
        mContentView.setVerticalScrollBarEnabled(false);
        initWebSetting();
        mContentView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                // Whatever
                loadnextpage();
            }

            @Override
            public void onSwipeRight() {

                loadpreviouspage();

            }

            @Override
            public void onSingleTouchConfirmed(MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    touchX = event.getX();
                    touchY = event.getY();
                    int mode = (int) (touchX % columnWidth);
                    if (screenX * 0.25 > mode) {
                        loadpreviouspage();
                        hide();
                    }
                    if (screenX * 0.75 < mode) {
                        loadnextpage();
                        hide();
                    }
                    if (screenX * 0.25 < mode && screenX * 0.75 > mode)
                        if (!mVisible) show();
                        else hide();
                }
//
            }

        });
    }

    public void initWebSetting() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        WebSettings webSetting = mContentView.getSettings();
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setDefaultTextEncodingName("utf-8");
        webSetting.setAllowFileAccess(true);
        webSetting.setAllowFileAccessFromFileURLs(true);
        mContentView.clearCache(true);
        mContentView.setHorizontalScrollBarEnabled(false);
        mContentView.setVerticalScrollBarEnabled(false);
        mContentView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
//                getScreenXY();
                final WebView myWebView = (WebView) view;
                String varMySheet = "var mySheet = document.styleSheets[0];";

                String addCSSRule = "function addCSSRule(selector, newRule) {"
                        + "ruleIndex = mySheet.cssRules.length;"
                        + "mySheet.insertRule(selector + '{' + newRule + ';}', ruleIndex);"
                        + "}";
                int columnWidth = myWebView.getMeasuredWidth() - 0;
                int columnGap = 0;
                final String insertRule1 = "addCSSRule('html', '"
                        + "padding: 20px 0px 20px 0px;"
                        + "font-size:120%;"
                        + "height: "
                        + ((columnHeight / getResources().getDisplayMetrics().density) - 40)
                        + "px;"
                        + " -webkit-column-gap: 0px; -moz-column-gap: 0px;  column-gap: 0px;"
                        + " -webkit-column-width: "
                        + (columnWidth - 0) + "px;"
                        + "-moz-column-width: "
                        + (columnWidth - 0) + "px;"
                        + "column-width:"
                        + (columnWidth - 0) + "px;')";

//                myWebView.loadUrl("javascript:" + varMySheet);
//                myWebView.loadUrl("javascript:" + addCSSRule);
//                myWebView.loadUrl("javascript:" + insertRule1);
                super.onPageFinished(view, url);
            }
        });


    }

    public void initRecyclerView() {
        mRecyclerViewChapter = (RecyclerView) findViewById(R.id.recyclerview_chapter);
        chapterAdapter = new ChapterAdapter(mRecyclerViewClickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        for (TableOfContent.Chapter c : mToc.getChapterList()) {
            System.out.println(c.getTitle());
        }
        chapterAdapter.addListTitle(mToc.getChapterList());
        mRecyclerViewChapter.setLayoutManager(layoutManager);
        mRecyclerViewChapter.setAdapter(chapterAdapter);
        mRecyclerViewChapter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View itemView = mRecyclerViewChapter.getChildAt(mPosition);
                TextView tv = itemView.findViewById(R.id.text_title);
                tv.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                mRecyclerViewChapter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }

    public void initCSS() {
        String cssFilePath = rootFolder + "/" + XNAME + "/epubviewer/css/" + cssFile;
        System.out.println("css:" + cssFilePath);
        File cssFile = new File(cssFilePath);
        IOFile ioFile = new IOFile();
        modeUtils.setSettingMode(mode);
        String css = modeUtils.getContentsCSS();
        System.out.println(css);
        ioFile.writeFile(cssFile, css);
    }

    public void initJS(String url) {
        IOFile ioFile = new IOFile();
        String jsFilePath = epubCommon.getUnzipPath() + File.separatorChar + jsFile;
        modeUtils.setPathfileHtml(url);
        File jsFile = new File(jsFilePath);
        ioFile.writeFile(jsFile, modeUtils.getContentsJS());
    }

    public void initHtmlLoader(String chapterPath) {
        System.out.println("chap:" + chapterPath);
        IOFile ioFile = new IOFile();
        HTMLParser parser = new HTMLParser();
        String cssString = parser.getLinkTagCSS(chapterPath);
        String str = rootFolder + "/";
        str = chapterPath.replace(str, "");
        StringBuilder builderCSS = new StringBuilder();
        StringBuilder builderJS = new StringBuilder();
        int tmp = 0;
        for (int i = 0; i < str.length(); i++) {
            if (String.valueOf(str.charAt(i)).equals("/")) {
                builderCSS.append("../");
                if (tmp > 0) builderJS.append("../");
                tmp++;
            }
        }
        String myCSS = "<link href=\"" + builderCSS.toString() + XNAME + "/epubviewer/css/" + cssFile + "\" rel=\"stylesheet\" type=\"text/css\" />\n" +
                "<link href=\"" + builderCSS.toString() + XNAME + "/epubviewer/css/reader-fonts.css\" rel=\"stylesheet\" type=\"text/css\" />\n";
        String myJS = "<script type=\"text/javascript\" src=\"" + builderJS.toString() + jsFile + "\"></script>\n" +
                "<script type=\"text/javascript\" src=\"" + builderJS.toString() + "onloadepubamazing.js\"></script>\n" + "</body>";
        cssString = myCSS + cssString + "</head>";
        File htmlFile = new File(urlLoader);
        String content = modeUtils.getContentHTML().replace("</head>", cssString);
        content = content.replace("</body>", myJS);
        ioFile.writeFile(htmlFile, content);
        Log.d("html", content);
    }

    public void loadRefreshPage() {
        loadEpubWebview(urlLoader);
        mContentView.scrollTo(presentPage * columnWidth, 0);
    }

    private void loadEpubWebview(String url) {
        url = ROOT_FILE + url;
        url = url.replaceAll(" ", "%20");
        mContentView.clearCache(true);
        mContentView.loadUrl(url);
        mContentView.scrollTo(presentPage * columnWidth, 0);
        savePageChapterFileCache();
    }

    public void savePageChapterFileCache() {
        String str = String.valueOf(presentPage);
        String jsCacheFile = epubCommon.getUnzipPath() + File.separatorChar + nameFileCache;
        IOFile rwFile = new IOFile();
        rwFile.saveCache(new File(jsCacheFile), str);
    }

    public void loadpreviouspage() {
        if (presentPage >= 1) {
            presentPage--;
            mContentView.scrollTo(presentPage * columnWidth, 0);
            savePageChapterFileCache();
        }
    }

    public void loadnextpage() {
        presentPage++;
        int x = presentPage * (columnWidth - 0);
        if ((presentPage) > mContentView.getContentWidth() / columnWidth - 1) {
//            presentPage = 0;
//            mContentView.scrollTo(0, 0);
            mPosition++;
            mRecyclerViewChapter.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    View itemView = mRecyclerViewChapter.getChildAt(mPosition);
                    TextView tv = itemView.findViewById(R.id.text_title);
                    tv.setTextColor(getApplicationContext().getResources().getColor(R.color.black));
                    mRecyclerViewChapter.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            mContentView.scrollTo(x, 0);
            savePageChapterFileCache();
        }
    }

    private void doBack() {
        finish();
    }

    private RecyclerViewClickListener mRecyclerViewClickListener = new RecyclerViewClickListener() {
        @Override
        public void onClick(View view, int position) {
            mPosition = position;
            TextView tv = view.findViewById(R.id.text_title);
            tv.setTextColor(getResources().getColor(R.color.black));
            TableOfContent.Chapter chapter = mToc.getChapterList().get(position);
            String pathChapter = chapter.getUrl();
            pathChapter = epubCommon.getBasePath() + File.separatorChar + pathChapter;
            String urlChapter = pathChapter.substring(
                    pathChapter.lastIndexOf("/") + 1);
            presentPage = 0;
            initJS(urlChapter);
//            loadEpubWebview(urlBlankPage);
            loadEpubWebview(urlLoader);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.epub_menu, menu);
        MenuItem item = menu.findItem(R.id.epub_menu_light);
        initMenu(item);
        return super.onCreateOptionsMenu(menu);
    }

    public void initMenu(MenuItem item) {
        if (mode.getBgColor().equals("black")) {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_moon_black));
        } else {
            item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sunny_white));
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.epub_menu_font_text:
                break;
            case R.id.epub_menu_light:
                if (mode.getBgColor().equals("white")) {
                    mode.setBgColor("black");
                    mode.setTextColor("white");
//                    ActionBar actionBar = getSupportActionBar();
//                    if (actionBar != null) {
//                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
//                    }
                    initCSS();
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_moon_black));
                    loadRefreshPage();
                } else {
                    mode.setBgColor("white");
                    mode.setTextColor("black");
//                    ActionBar actionBar = getSupportActionBar();
//                    if (actionBar != null) {
//                        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
//                    }
                    initCSS();
                    item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_sunny_white));
                    loadRefreshPage();
                }
                break;
            case android.R.id.home:
                Intent homeIntent = new Intent(this, MainActivity.class);
                homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                break;
            case R.id.epub_menu_list:

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
//                    hide();
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private ProgressBar mProgressBar;

        public DownloadTask(Context context) {
            this.context = context;
            mProgressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.getProgressDrawable().setColorFilter(context.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            params.setMargins(screenX / 3, 0, screenX / 3, 0);
            mDrawerLayout.addView(mProgressBar, params);
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
//                String dir = getFilesDir() + "/" + "nomedia";
                fileDownloaderPath = rootFolder + "/" + 567 + ".epub";
                output = new FileOutputStream(fileDownloaderPath);
                Log.d("pathDowload", rootFolder);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
                // flushing output
                output.flush();
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setMax(100);
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.setMax(100);
//            mProgressDialog.setProgress(progress[0]);
            mProgressBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
//            mProgressDialog.dismiss();
            mProgressBar.setVisibility(View.GONE);
            mDrawerLayout.removeView(mProgressBar);
            if (result != null) {
                Log.d("result Dowload", result.toString());
                Toast.makeText(context, "Download error: " + result, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();

                new PrepareBookAsyncTask(EpubViewerActivity.this).execute();
                Toast.makeText(context, "File downloaded", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private final class PrepareBookAsyncTask extends AsyncTask<Void, Void, Void> {
        protected WeakReference<EpubViewerActivity> readRfc;
        protected boolean isCancel = false;
        private IOFile ioFile;


        public PrepareBookAsyncTask(EpubViewerActivity activity) {
            readRfc = new WeakReference<EpubViewerActivity>(activity);
            ioFile = new IOFile();
//        mProgressDialog = new ProgressDialog(activity);
//        mProgressDialog.show();
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... params) {
            EpubViewerActivity activity = readRfc.get();
            try {
                epubCommon = new EpubCommon(fileDownloaderPath, null, false);
                epubBook = epubCommon.parseBookInfo();
                mToc = epubCommon.parseTableOfContent();
                String basePath = epubCommon.getBasePath();
                System.out.println("base:" + basePath);
                String firstChapterPath = basePath + File.separatorChar + mToc.getChapter(mPosition).getUrl();
                urlLoader = firstChapterPath.substring(0, firstChapterPath.lastIndexOf("/") + 1) + htmlFile;

                System.out.println("u:" + firstChapterPath);
                String jsFilePath = epubCommon.getUnzipPath() + File.separatorChar + jsFile;
                File jsFile = new File(jsFilePath);
                if (!jsFile.exists()) {
                    String urlChapter = firstChapterPath.substring(
                            firstChapterPath.lastIndexOf("/") + 1);
//                    indexFile.mkdirs();
                    Log.d("hello", "hi");
//                    jsFile.mkdirs();
                    initHtmlLoader(firstChapterPath);
                    initJS(urlChapter);
                    String jsCacheFile = epubCommon.getUnzipPath() + File.separatorChar + nameFileCache;
                    File f = new File(jsCacheFile);
                    ioFile.saveCache(f, String.valueOf(presentPage));


                } else {
                    String jsCacheFile = epubCommon.getUnzipPath() + File.separatorChar + nameFileCache;
                    String page = ioFile.getDataCache(jsCacheFile);
                    presentPage = Integer.parseInt(page);
                }
            } catch (Exception e) {
                e.printStackTrace();
                activity.doBack();
            }

            if (isCancelled()) {
                isCancel = true;
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.i(this.toString(), "PrepareBookAsyncTask onPreExecute");
        }

        @Override
        protected void onPostExecute(Void result) {
            EpubViewerActivity read = readRfc.get();
            if (read != null) {
                initRecyclerView();
                title_book_tv.setText(epubBook.title);
                read.loadEpubWebview(urlLoader);
//            mProgressDialog.dismiss();
            }
        }

//            String cover_url = mEpubCommon.getBasePath() + "/Images/cover.jpg";
//            Log.d(this.toString(), "coverPath: " + cover_url);
//
//            Bitmap bmImg = BitmapFactory.decodeFile(cover_url);
//            cover_img.setImageBitmap(bmImg);
    }


}
