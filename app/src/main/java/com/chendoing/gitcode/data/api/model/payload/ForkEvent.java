package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.R;
import com.chendoing.gitcode.data.api.model.Forkee;
import com.chendoing.gitcode.data.api.model.User;

/**
 * url : https://api.github.com/repos/baxterthehacker/public-repo/deployments/710692
 * id : 710692
 * sha : 9049f1265b7d61be4a8904a9a27120d2064dab3b
 * ref : master
 * task : deploy
 * payload : {}
 * environment : production
 * description : null
 * creator : {"login":"baxterthehacker","id":6752317,"avatar_url":"https://avatars.githubusercontent.com/u/6752317?v=3","gravatar_id":"","url":"https://api.github.com/users/baxterthehacker","html_url":"https://github.com/baxterthehacker","followers_url":"https://api.github.com/users/baxterthehacker/followers","following_url":"https://api.github.com/users/baxterthehacker/following{/other_user}","gists_url":"https://api.github.com/users/baxterthehacker/gists{/gist_id}","starred_url":"https://api.github.com/users/baxterthehacker/starred{/owner}{/repo}","subscriptions_url":"https://api.github.com/users/baxterthehacker/subscriptions","organizations_url":"https://api.github.com/users/baxterthehacker/orgs","repos_url":"https://api.github.com/users/baxterthehacker/repos","events_url":"https://api.github.com/users/baxterthehacker/events{/privacy}","received_events_url":"https://api.github.com/users/baxterthehacker/received_events","type":"User","site_admin":false}
 * created_at : 2015-05-05T23:40:38Z
 * updated_at : 2015-05-05T23:40:38Z
 * statuses_url : https://api.github.com/repos/baxterthehacker/public-repo/deployments/710692/statuses
 * repository_url : https://api.github.com/repos/baxterthehacker/public-repo
 */
public class ForkEvent extends Payloaded {


    private Forkee forkee;

    public Forkee getForkee() {
        return forkee;
    }

    public void setForkee(Forkee forkee) {
        this.forkee = forkee;
    }

    @Override
    public int getDrawable() {
        return R.drawable.mega_icon_fork;
    }

    @Override
    public String toString() {
        return "ForkEvent{" +
                "forkee=" + forkee +
                '}';
    }
}
