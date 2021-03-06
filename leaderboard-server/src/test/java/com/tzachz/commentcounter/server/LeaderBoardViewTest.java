package com.tzachz.commentcounter.server;

import com.google.common.collect.Lists;
import com.tzachz.commentcounter.CommentBuilder;
import com.tzachz.commentcounter.Commenter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: tzachz
 * Date: 17/08/13
 * Time: 18:36
 */
public class LeaderBoardViewTest {

    private CommentBuilder commentBuilder = new CommentBuilder();

    @Test
    public void singleCommentAlwaysChosen() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "body"));
        LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), "org1", true);
        assertThat(view.getRecords().get(0).getSampleComment(), equalTo("body"));
    }

    @Test
    public void fewCommentsRandomChosen() throws Exception {
        Commenter commenter = new Commenter("user1");
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "body1"));
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "body2"));
        commenter.addComment(commentBuilder.createComment("user1", "some-url", "body3"));
        int body1Chosen = 0;
        for (int i = 0; i < 100; i++) {
            LeaderBoardView view = new LeaderBoardView(Lists.newArrayList(commenter), "org1", true);
            body1Chosen += view.getRecords().get(0).getSampleComment().equals("body1") ? 1 : 0;
        }
        assertThat(body1Chosen, is(both(greaterThan(10)).and(lessThan(60))));
    }
}
