package textformat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.util.converter.CurrencyStringConverter;

import com.sun.javafx.scene.control.behavior.SpinnerBehavior;
import com.sun.javafx.scene.control.skin.SpinnerSkin;

/**
 *
 * @author Yucchi
 */
public class FXMLDocumentController implements Initializable {

    private static final Number INITAL_VALUE = 0;
    private BooleanProperty poor;

    private BooleanProperty rich;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Label label_1;

    @FXML
    private Spinner<Integer> spinner;

    @FXML
    private Label label_2;

    private SpinnerBehavior<Integer> beh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        spinner.setEditable(true);

    // 書式設定
        CurrencyStringConverter currencyStringConverter = new CurrencyStringConverter();
        TextFormatter<Number> formatter = new TextFormatter<>(currencyStringConverter);

//      // 動くけど例外吐くからダメ (>_<｡)
//      formatter.valueProperty().bindBidirectional((Property)spinner.valueProperty());

    // スピナーに書式設定
        spinner.getEditor().setTextFormatter(formatter);

        // バインド例外吐くから・・・
        formatter.setValue(INITAL_VALUE);

        // アプリケーション起動時のスタイル
        spinner.getEditor().setStyle("-fx-text-fill: black; -fx-font: 14pt 'serif'; -fx-alignment: CENTER_RIGHT;");

        // Spinner のデザインを変更
        spinner.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);

        spinner.valueProperty().addListener((ov, oldValue, newValue) -> {

            // バインド例外吐くから・・・
            formatter.setValue(newValue);

            // RT-40257  https://javafx-jira.kenai.com/browse/RT-40257
            if (beh == null) {
                beh = (SpinnerBehavior) ((SpinnerSkin) (spinner.getSkin())).getBehavior();
                spinner.focusedProperty().addListener((obs, b, b1) -> {
                    if (b && !b1) {
                        beh.stopSpinning();
                    }
                });
            }

        // スピナーのスタイルを変更する
            if (newValue <= 30_000) {
                spinner.getEditor().setStyle("-fx-text-fill: red; -fx-font: italic bold 14pt 'serif'; -fx-alignment: CENTER_RIGHT;");
            }else{
                if (newValue >= 70_000) {
                spinner.getEditor().setStyle("-fx-text-fill: blue; -fx-font: italic bold 14pt 'serif'; -fx-alignment: CENTER_RIGHT;");
            }else{
                  spinner.getEditor().setStyle("-fx-text-fill: black; -fx-font: 14pt 'serif'; -fx-alignment: CENTER_RIGHT;");
                }
            }

            if (newValue <= 30_000 && poor.getValue()) {
                poor.setValue(Boolean.FALSE);
                showPoorDialog();
            }
            if (newValue >= 70_000 && rich.getValue()) {
                rich.setValue(Boolean.FALSE);
                showRichDialog();
            }

        });

    }

    private void showPoorDialog() {
        Alert alert = new Alert(AlertType.WARNING, "もう少しお小遣いを増やしましょう。", ButtonType.OK);
        alert.initModality(Modality.NONE);
        alert.initOwner(anchorPane.getScene().getWindow());
        alert.getDialogPane().setHeaderText("少ないですね。");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println(":p"));
    }

    private void showRichDialog() {
        Alert alert = new Alert(AlertType.INFORMATION, "お金で愛は買えないが、お金で愛は潤います。", ButtonType.OK);
        alert.initModality(Modality.NONE);
        alert.initOwner(anchorPane.getScene().getWindow());
        alert.getDialogPane().setHeaderText("幸せですね！");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println(":p"));
    }

}
