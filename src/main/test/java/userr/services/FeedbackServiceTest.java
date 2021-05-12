package userr.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import userr.exceptions.*;
import userr.model.Feedback;
import userr.model.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackServiceTest {
    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".testfb-registration";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        FeedbackService.initDatabase();
    }
    @AfterEach
    void tearDown() {
        FeedbackService.getDatabase().close();
    }
    @Test
    @DisplayName("Database is initialize, there are no feedbacks")
    void DatabaseIsInitializeAndNoFeedbacksArePersisted() {
        assertThat(FeedbackService.getAllFeedbacks()).isNotNull();
        assertThat(FeedbackService.getAllFeedbacks()).isEmpty();
    }

    @Test
    @DisplayName("Feedback is successfully persisted to Database ")
    void testFeedbackIsAddedToDatabase() throws FieldNotCompletedException, FeedbackAlreadyExistsException {
        FeedbackService.addFeedback("kris","fain","9");
        assertThat(FeedbackService.getAllFeedbacks()).isNotEmpty();
        assertThat(FeedbackService.getAllFeedbacks()).size().isEqualTo(1);
        Feedback fb = FeedbackService.getAllFeedbacks().get(0);
        assertThat(fb).isNotNull();
        assertThat(fb.getLoggedUser()).isEqualTo("kris");
        assertThat(fb.getDescription()).isEqualTo("fain");
        assertThat(fb.getRate()).isEqualTo("9");

    }

    @Test
    @DisplayName("Check unique feedback")
    void testUniqueFeedback() throws FeedbackAlreadyExistsException, FieldNotCompletedException {
        FeedbackService.addFeedback("kris","fain","9");
        assertThrows(FeedbackAlreadyExistsException.class,()->{
            FeedbackService.checkUniqueFeedback("kris");
        });
    }
    @Test
    @DisplayName("Check all fields complete for feedback")
    void testFieldsComplete() {
        assertThrows(FieldNotCompletedException.class,()->{
            FeedbackService.checkAllFieldCompleted("","");
        });
    }
}