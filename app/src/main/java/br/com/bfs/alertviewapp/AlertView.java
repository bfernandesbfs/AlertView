package br.com.bfs.alertviewapp;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * Alert View
 * <p/>
 * BFS - 02/02/16
 * Created by brunofernandes.
 */
public class AlertView {

    public enum AlertType {
        LOAD,
        DEFAULT
    }

    private Activity activity;
    private Dialog dialog;
    private ImageView ivIcon;
    private TextView textTitle;
    private ProgressBar progressBar;
    private LinearLayout container, containerBody, containerHeader, containerFooter;
    private int numberOfButtons = 0;
    private AlertType alertType = AlertType.DEFAULT;
    private Typeface typeface, typefaceButton;

    /*
    Initialize Class
     */
    public static AlertView with(Activity activity, String title) {
        return new AlertView(activity, title, false);
    }

    public static AlertView withLoad(Activity activity, String title) {
        return new AlertView(activity, title, true);
    }

    private AlertView(Activity activity, String title, boolean isLoad) {
        this.activity = activity;
        if (isLoad) {
            alertType = AlertType.LOAD;
            initializeLoad(title);
        } else {
            alertType = AlertType.DEFAULT;
            createDialog();
            setTitle(title);
        }
    }

    /*
    Public Methods
     */
    public void show() {
        if (alertType == AlertType.DEFAULT) {
            setResizeButtons();
        }
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public AlertView change(AlertType alertType, String title, int icon) {
        this.alertType = alertType;
        changeAlert(title, icon);
        return this;
    }

    public AlertView setContainerLayout(int resource) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View viewContainer = inflater.inflate(resource, null);
        containerBody.addView(viewContainer);
        return this;
    }

    public AlertView setContainerLayout(View view) {
        containerBody.addView(view);
        return this;
    }

    public AlertView setTypeFace(Typeface typeface) {
        this.typeface = typeface;
        textTitle.setTypeface(typeface);
        return this;
    }

    public AlertView setTypeFaceButton(Typeface typeface) {
        this.typefaceButton = typeface;
        return this;
    }

    public AlertView setMessage(String message) {

        TextView textView = new TextView(activity);
        textView.setText(message);
        if (typeface != null) {
            textView.setTypeface(typeface);
        }
        containerBody.addView(textView);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        layoutParams.setMargins(48, 48, 48, 48);
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        textView.setLayoutParams(layoutParams);

        return this;
    }

    public AlertView setHeaderColor(int color) {
        containerHeader.setBackgroundColor(ContextCompat.getColor(activity, color));
        return this;
    }

    public AlertView setFooterColor(int color) {
        containerFooter.setBackgroundColor(ContextCompat.getColor(activity, color));
        return this;
    }

    public AlertView setContainerColor(int color) {
        containerBody.setBackgroundColor(ContextCompat.getColor(activity, color));
        return this;
    }

    public AlertView setProgressBarColor(int color) {
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, color), PorterDuff.Mode.SRC_IN);
        return this;
    }

    public AlertView setHeaderIcon(int icon) {
        if (icon > 0) {
            ivIcon.setImageResource(icon);
        } else {
            ivIcon.setVisibility(View.GONE);
        }
        return this;
    }

    public AlertView setCancelableDialog(boolean cancelableDialog) {
        dialog.setCancelable(cancelableDialog);
        return this;
    }

    public AlertView addButtonOk(String title, View.OnClickListener onClickListener) {
        if (numberOfButtons < 2) {
            addButton(R.id.btnOk, title, onClickListener);
            numberOfButtons++;
            setResizeButtons();
        }
        return this;
    }

    public AlertView addButtonOk(String title, int textColor, int backgroundColor, View.OnClickListener onClickListener) {
        if (numberOfButtons < 2) {
            Button button = addButton(R.id.btnOk, title, onClickListener);
            button.setTextColor(ContextCompat.getColor(activity, textColor));
            button.setBackgroundResource(backgroundColor);
            numberOfButtons++;
            setResizeButtons();
        }
        return this;
    }

    public AlertView addButtonDestroy(String title) {
        if (numberOfButtons < 2) {
            addButton(R.id.btnCancel, title, null);
            numberOfButtons++;
            setResizeButtons();
        }
        return this;
    }

    public AlertView addButtonDestroy(String title, int textColor, int backgroundColor) {
        if (numberOfButtons < 2) {
            Button button = addButton(R.id.btnCancel, title, null);
            button.setTextColor(ContextCompat.getColor(activity, textColor));
            button.setBackgroundResource(backgroundColor);
            numberOfButtons++;
            setResizeButtons();
        }
        return this;
    }

    /*
    Private Methods
     */
    private void initializeLoad(String title) {
        setDialog();
        createAlertLoad(title);
    }

    private void createDialog() {
        setDialog();
    }

    private void changeAlert(String title, int icon) {

        containerBody.removeAllViewsInLayout();

        if (alertType == AlertType.LOAD) {
            createAlertLoad(title);
        } else {
            containerHeader.setVisibility(View.VISIBLE);
            containerFooter.setVisibility(View.VISIBLE);

            Button button = (Button) dialog.findViewById(R.id.btnOk);
            button.setVisibility(View.GONE);
            button.setOnClickListener(null);

            button = (Button) dialog.findViewById(R.id.btnCancel);
            button.setVisibility(View.GONE);
            button.setOnClickListener(null);

            numberOfButtons = 0;

            setTitle(title);
            setHeaderIcon(icon);
        }


    }

    private void setupFindById() {
        container = (LinearLayout) dialog.findViewById(R.id.container);

        containerHeader = (LinearLayout) dialog.findViewById(R.id.containerHeader);
        ivIcon = (ImageView) dialog.findViewById(R.id.ivIcon);
        textTitle = (TextView) dialog.findViewById(R.id.textTitle);

        containerBody = (LinearLayout) dialog.findViewById(R.id.containerBody);
        containerFooter = (LinearLayout) dialog.findViewById(R.id.containerFooter);
    }

    private void setDialog() {
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert_main_view);

        setupFindById();
    }

    private void createAlertLoad(String title) {
        containerHeader.setVisibility(View.GONE);
        containerFooter.setVisibility(View.GONE);

        progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);

        containerBody.addView(progressBar);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) progressBar.getLayoutParams();
        layoutParams.setMargins(48, 48, 48, 0);
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        progressBar.setLayoutParams(layoutParams);

        if (title != null) {
            TextView tvTitle = new TextView(activity);
            tvTitle.setText(title);
            tvTitle.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

            tvTitle.setLayoutParams(layoutParams);

            containerBody.addView(tvTitle);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tvTitle.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            layoutParams.gravity = Gravity.CENTER;

            tvTitle.setLayoutParams(params);
        }

    }

    private void setTitle(String title) {

        if (title != null) {
            textTitle.setText(title);
        } else {
            containerHeader.setVisibility(View.GONE);
        }
    }

    private Button addButton(int resource, String title, View.OnClickListener onClickListener) {

        Button button = (Button) dialog.findViewById(resource);
        button.setVisibility(View.VISIBLE);
        button.setText(title);
        if (typeface != null || typefaceButton != null)
            button.setTypeface(typefaceButton == null ? typeface : typefaceButton);

        setOnClick(button, onClickListener);
        return button;
    }

    private void setOnClick(Button button, View.OnClickListener onClickListener) {

        if (onClickListener == null) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        } else {
            button.setOnClickListener(onClickListener);
        }
    }

    private void setResizeButtons() {

        if (numberOfButtons > 0) {
            Button button = (Button) dialog.findViewById(R.id.btnOk);

            if (button.getVisibility() == View.VISIBLE) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                layoutParams.weight = numberOfButtons > 1 ? 1 : 2;
                button.setLayoutParams(layoutParams);
            }

            button = (Button) dialog.findViewById(R.id.btnCancel);

            if (button.getVisibility() == View.VISIBLE) {

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) button.getLayoutParams();
                layoutParams.weight = numberOfButtons > 1 ? 1 : 2;
                button.setLayoutParams(layoutParams);
            }

        } else {
            containerFooter.setVisibility(View.GONE);
        }
    }
}
