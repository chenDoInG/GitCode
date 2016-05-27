package com.chendoing.gitcode.data.api.model.payload;

import com.chendoing.gitcode.data.api.model.Event;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by chenDoInG on 16/5/27.
 */
public class WatchPayloadTest {


    @Test
    public void testGetAction() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Event> event = mapper.readValue(eventJson, new TypeReference<List<Event>>() {
        });
        Assert.assertEquals("", ((WatchEvent)event.get(0).getPayload()).getAction());
    }

    @Test
    public void forkEvent() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JodaModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Event event = mapper.readValue(forkJson,Event.class);
        ForkEvent forkEvent = (ForkEvent) event.getPayload();
        Assert.assertEquals("",forkEvent.getRepository().getFull_name());
    }
    private static String forkJson =
            "{\n" +
                    "    \"type\": \"ForkEvent\",\n" +
                    "    \"public\": true,\n" +
                    "    \"payload\": {\n" +
                    "        \"forkee\": {\n" +
                    "            \"id\": 35129393,\n" +
                    "            \"name\": \"public-repo\",\n" +
                    "            \"full_name\": \"baxterandthehackers/public-repo\",\n" +
                    "            \"owner\": {\n" +
                    "                \"login\": \"baxterandthehackers\",\n" +
                    "                \"id\": 7649605,\n" +
                    "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/7649605?v=3\",\n" +
                    "                \"gravatar_id\": \"\",\n" +
                    "                \"url\": \"https://api.github.com/users/baxterandthehackers\",\n" +
                    "                \"html_url\": \"https://github.com/baxterandthehackers\",\n" +
                    "                \"followers_url\": \"https://api.github.com/users/baxterandthehackers/followers\",\n" +
                    "                \"following_url\": \"https://api.github.com/users/baxterandthehackers/following{/other_user}\",\n" +
                    "                \"gists_url\": \"https://api.github.com/users/baxterandthehackers/gists{/gist_id}\",\n" +
                    "                \"starred_url\": \"https://api.github.com/users/baxterandthehackers/starred{/owner}{/repo}\",\n" +
                    "                \"subscriptions_url\": \"https://api.github.com/users/baxterandthehackers/subscriptions\",\n" +
                    "                \"organizations_url\": \"https://api.github.com/users/baxterandthehackers/orgs\",\n" +
                    "                \"repos_url\": \"https://api.github.com/users/baxterandthehackers/repos\",\n" +
                    "                \"events_url\": \"https://api.github.com/users/baxterandthehackers/events{/privacy}\",\n" +
                    "                \"received_events_url\": \"https://api.github.com/users/baxterandthehackers/received_events\",\n" +
                    "                \"type\": \"Organization\",\n" +
                    "                \"site_admin\": false\n" +
                    "            },\n" +
                    "            \"private\": false,\n" +
                    "            \"html_url\": \"https://github.com/baxterandthehackers/public-repo\",\n" +
                    "            \"description\": \"\",\n" +
                    "            \"fork\": true,\n" +
                    "            \"url\": \"https://api.github.com/repos/baxterandthehackers/public-repo\",\n" +
                    "            \"forks_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/forks\",\n" +
                    "            \"keys_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/keys{/key_id}\",\n" +
                    "            \"collaborators_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/collaborators{/collaborator}\",\n" +
                    "            \"teams_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/teams\",\n" +
                    "            \"hooks_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/hooks\",\n" +
                    "            \"issue_events_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/issues/events{/number}\",\n" +
                    "            \"events_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/events\",\n" +
                    "            \"assignees_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/assignees{/user}\",\n" +
                    "            \"branches_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/branches{/branch}\",\n" +
                    "            \"tags_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/tags\",\n" +
                    "            \"blobs_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/git/blobs{/sha}\",\n" +
                    "            \"git_tags_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/git/tags{/sha}\",\n" +
                    "            \"git_refs_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/git/refs{/sha}\",\n" +
                    "            \"trees_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/git/trees{/sha}\",\n" +
                    "            \"statuses_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/statuses/{sha}\",\n" +
                    "            \"languages_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/languages\",\n" +
                    "            \"stargazers_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/stargazers\",\n" +
                    "            \"contributors_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/contributors\",\n" +
                    "            \"subscribers_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/subscribers\",\n" +
                    "            \"subscription_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/subscription\",\n" +
                    "            \"commits_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/commits{/sha}\",\n" +
                    "            \"git_commits_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/git/commits{/sha}\",\n" +
                    "            \"comments_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/comments{/number}\",\n" +
                    "            \"issue_comment_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/issues/comments{/number}\",\n" +
                    "            \"contents_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/contents/{+path}\",\n" +
                    "            \"compare_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/compare/{base}...{head}\",\n" +
                    "            \"merges_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/merges\",\n" +
                    "            \"archive_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/{archive_format}{/ref}\",\n" +
                    "            \"downloads_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/downloads\",\n" +
                    "            \"issues_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/issues{/number}\",\n" +
                    "            \"pulls_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/pulls{/number}\",\n" +
                    "            \"milestones_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/milestones{/number}\",\n" +
                    "            \"notifications_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/notifications{?since,all,participating}\",\n" +
                    "            \"labels_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/labels{/name}\",\n" +
                    "            \"releases_url\": \"https://api.github.com/repos/baxterandthehackers/public-repo/releases{/id}\",\n" +
                    "            \"created_at\": \"2015-05-05T23:40:30Z\",\n" +
                    "            \"updated_at\": \"2015-05-05T23:40:30Z\",\n" +
                    "            \"pushed_at\": \"2015-05-05T23:40:27Z\",\n" +
                    "            \"git_url\": \"git://github.com/baxterandthehackers/public-repo.git\",\n" +
                    "            \"ssh_url\": \"git@github.com:baxterandthehackers/public-repo.git\",\n" +
                    "            \"clone_url\": \"https://github.com/baxterandthehackers/public-repo.git\",\n" +
                    "            \"svn_url\": \"https://github.com/baxterandthehackers/public-repo\",\n" +
                    "            \"homepage\": null,\n" +
                    "            \"size\": 0,\n" +
                    "            \"stargazers_count\": 0,\n" +
                    "            \"watchers_count\": 0,\n" +
                    "            \"language\": null,\n" +
                    "            \"has_issues\": false,\n" +
                    "            \"has_downloads\": true,\n" +
                    "            \"has_wiki\": true,\n" +
                    "            \"has_pages\": true,\n" +
                    "            \"forks_count\": 0,\n" +
                    "            \"mirror_url\": null,\n" +
                    "            \"open_issues_count\": 0,\n" +
                    "            \"forks\": 0,\n" +
                    "            \"open_issues\": 0,\n" +
                    "            \"watchers\": 0,\n" +
                    "            \"default_branch\": \"master\",\n" +
                    "            \"public\": true\n" +
                    "        },\n" +
                    "        \"repository\": {\n" +
                    "            \"id\": 35129377,\n" +
                    "            \"name\": \"public-repo\",\n" +
                    "            \"full_name\": \"baxterthehacker/public-repo\",\n" +
                    "            \"owner\": {\n" +
                    "                \"login\": \"baxterthehacker\",\n" +
                    "                \"id\": 6752317,\n" +
                    "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/6752317?v=3\",\n" +
                    "                \"gravatar_id\": \"\",\n" +
                    "                \"url\": \"https://api.github.com/users/baxterthehacker\",\n" +
                    "                \"html_url\": \"https://github.com/baxterthehacker\",\n" +
                    "                \"followers_url\": \"https://api.github.com/users/baxterthehacker/followers\",\n" +
                    "                \"following_url\": \"https://api.github.com/users/baxterthehacker/following{/other_user}\",\n" +
                    "                \"gists_url\": \"https://api.github.com/users/baxterthehacker/gists{/gist_id}\",\n" +
                    "                \"starred_url\": \"https://api.github.com/users/baxterthehacker/starred{/owner}{/repo}\",\n" +
                    "                \"subscriptions_url\": \"https://api.github.com/users/baxterthehacker/subscriptions\",\n" +
                    "                \"organizations_url\": \"https://api.github.com/users/baxterthehacker/orgs\",\n" +
                    "                \"repos_url\": \"https://api.github.com/users/baxterthehacker/repos\",\n" +
                    "                \"events_url\": \"https://api.github.com/users/baxterthehacker/events{/privacy}\",\n" +
                    "                \"received_events_url\": \"https://api.github.com/users/baxterthehacker/received_events\",\n" +
                    "                \"type\": \"User\",\n" +
                    "                \"site_admin\": false\n" +
                    "            },\n" +
                    "            \"private\": false,\n" +
                    "            \"html_url\": \"https://github.com/baxterthehacker/public-repo\",\n" +
                    "            \"description\": \"\",\n" +
                    "            \"fork\": false,\n" +
                    "            \"url\": \"https://api.github.com/repos/baxterthehacker/public-repo\",\n" +
                    "            \"forks_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/forks\",\n" +
                    "            \"keys_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/keys{/key_id}\",\n" +
                    "            \"collaborators_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/collaborators{/collaborator}\",\n" +
                    "            \"teams_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/teams\",\n" +
                    "            \"hooks_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/hooks\",\n" +
                    "            \"issue_events_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues/events{/number}\",\n" +
                    "            \"events_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/events\",\n" +
                    "            \"assignees_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/assignees{/user}\",\n" +
                    "            \"branches_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/branches{/branch}\",\n" +
                    "            \"tags_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/tags\",\n" +
                    "            \"blobs_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/blobs{/sha}\",\n" +
                    "            \"git_tags_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/tags{/sha}\",\n" +
                    "            \"git_refs_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/refs{/sha}\",\n" +
                    "            \"trees_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/trees{/sha}\",\n" +
                    "            \"statuses_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/statuses/{sha}\",\n" +
                    "            \"languages_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/languages\",\n" +
                    "            \"stargazers_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/stargazers\",\n" +
                    "            \"contributors_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/contributors\",\n" +
                    "            \"subscribers_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/subscribers\",\n" +
                    "            \"subscription_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/subscription\",\n" +
                    "            \"commits_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/commits{/sha}\",\n" +
                    "            \"git_commits_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/commits{/sha}\",\n" +
                    "            \"comments_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/comments{/number}\",\n" +
                    "            \"issue_comment_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues/comments{/number}\",\n" +
                    "            \"contents_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/contents/{+path}\",\n" +
                    "            \"compare_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/compare/{base}...{head}\",\n" +
                    "            \"merges_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/merges\",\n" +
                    "            \"archive_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/{archive_format}{/ref}\",\n" +
                    "            \"downloads_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/downloads\",\n" +
                    "            \"issues_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues{/number}\",\n" +
                    "            \"pulls_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/pulls{/number}\",\n" +
                    "            \"milestones_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/milestones{/number}\",\n" +
                    "            \"notifications_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/notifications{?since,all,participating}\",\n" +
                    "            \"labels_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/labels{/name}\",\n" +
                    "            \"releases_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/releases{/id}\",\n" +
                    "            \"created_at\": \"2015-05-05T23:40:12Z\",\n" +
                    "            \"updated_at\": \"2015-05-05T23:40:30Z\",\n" +
                    "            \"pushed_at\": \"2015-05-05T23:40:27Z\",\n" +
                    "            \"git_url\": \"git://github.com/baxterthehacker/public-repo.git\",\n" +
                    "            \"ssh_url\": \"git@github.com:baxterthehacker/public-repo.git\",\n" +
                    "            \"clone_url\": \"https://github.com/baxterthehacker/public-repo.git\",\n" +
                    "            \"svn_url\": \"https://github.com/baxterthehacker/public-repo\",\n" +
                    "            \"homepage\": null,\n" +
                    "            \"size\": 0,\n" +
                    "            \"stargazers_count\": 0,\n" +
                    "            \"watchers_count\": 0,\n" +
                    "            \"language\": null,\n" +
                    "            \"has_issues\": true,\n" +
                    "            \"has_downloads\": true,\n" +
                    "            \"has_wiki\": true,\n" +
                    "            \"has_pages\": true,\n" +
                    "            \"forks_count\": 1,\n" +
                    "            \"mirror_url\": null,\n" +
                    "            \"open_issues_count\": 2,\n" +
                    "            \"forks\": 1,\n" +
                    "            \"open_issues\": 2,\n" +
                    "            \"watchers\": 0,\n" +
                    "            \"default_branch\": \"master\"\n" +
                    "        },\n" +
                    "        \"sender\": {\n" +
                    "            \"login\": \"baxterandthehackers\",\n" +
                    "            \"id\": 7649605,\n" +
                    "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/7649605?v=3\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"url\": \"https://api.github.com/users/baxterandthehackers\",\n" +
                    "            \"html_url\": \"https://github.com/baxterandthehackers\",\n" +
                    "            \"followers_url\": \"https://api.github.com/users/baxterandthehackers/followers\",\n" +
                    "            \"following_url\": \"https://api.github.com/users/baxterandthehackers/following{/other_user}\",\n" +
                    "            \"gists_url\": \"https://api.github.com/users/baxterandthehackers/gists{/gist_id}\",\n" +
                    "            \"starred_url\": \"https://api.github.com/users/baxterandthehackers/starred{/owner}{/repo}\",\n" +
                    "            \"subscriptions_url\": \"https://api.github.com/users/baxterandthehackers/subscriptions\",\n" +
                    "            \"organizations_url\": \"https://api.github.com/users/baxterandthehackers/orgs\",\n" +
                    "            \"repos_url\": \"https://api.github.com/users/baxterandthehackers/repos\",\n" +
                    "            \"events_url\": \"https://api.github.com/users/baxterandthehackers/events{/privacy}\",\n" +
                    "            \"received_events_url\": \"https://api.github.com/users/baxterandthehackers/received_events\",\n" +
                    "            \"type\": \"Organization\",\n" +
                    "            \"site_admin\": false\n" +
                    "        }\n" +
                    "    },\n" +
                    "    \"repo\": {\n" +
                    "        \"id\": 3,\n" +
                    "        \"name\": \"octocat/Hello-World\",\n" +
                    "        \"url\": \"https://api.github.com/repos/octocat/Hello-World\"\n" +
                    "    },\n" +
                    "    \"actor\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"login\": \"octocat\",\n" +
                    "        \"gravatar_id\": \"\",\n" +
                    "        \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
                    "        \"url\": \"https://api.github.com/users/octocat\"\n" +
                    "    },\n" +
                    "    \"org\": {\n" +
                    "        \"id\": 1,\n" +
                    "        \"login\": \"github\",\n" +
                    "        \"gravatar_id\": \"\",\n" +
                    "        \"url\": \"https://api.github.com/orgs/github\",\n" +
                    "        \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\"\n" +
                    "    },\n" +
                    "    \"created_at\": \"2011-09-06T17:26:27Z\",\n" +
                    "    \"id\": \"12345\"\n" +
                    "}";

    private static String eventJson =
            "[\n" +
                    "    {\n" +
                    "        \"type\": \"WatchEvent\",\n" +
                    "        \"public\": true,\n" +
                    "        \"payload\": {\n" +
                    "            \"action\": \"started\",\n" +
                    "            \"repository\": {\n" +
                    "                \"id\": 35129377,\n" +
                    "                \"name\": \"public-repo\",\n" +
                    "                \"full_name\": \"baxterthehacker/public-repo\",\n" +
                    "                \"owner\": {\n" +
                    "                    \"login\": \"baxterthehacker\",\n" +
                    "                    \"id\": 6752317,\n" +
                    "                    \"avatar_url\": \"https://avatars.githubusercontent.com/u/6752317?v=3\",\n" +
                    "                    \"gravatar_id\": \"\",\n" +
                    "                    \"url\": \"https://api.github.com/users/baxterthehacker\",\n" +
                    "                    \"html_url\": \"https://github.com/baxterthehacker\",\n" +
                    "                    \"followers_url\": \"https://api.github.com/users/baxterthehacker/followers\",\n" +
                    "                    \"following_url\": \"https://api.github.com/users/baxterthehacker/following{/other_user}\",\n" +
                    "                    \"gists_url\": \"https://api.github.com/users/baxterthehacker/gists{/gist_id}\",\n" +
                    "                    \"starred_url\": \"https://api.github.com/users/baxterthehacker/starred{/owner}{/repo}\",\n" +
                    "                    \"subscriptions_url\": \"https://api.github.com/users/baxterthehacker/subscriptions\",\n" +
                    "                    \"organizations_url\": \"https://api.github.com/users/baxterthehacker/orgs\",\n" +
                    "                    \"repos_url\": \"https://api.github.com/users/baxterthehacker/repos\",\n" +
                    "                    \"events_url\": \"https://api.github.com/users/baxterthehacker/events{/privacy}\",\n" +
                    "                    \"received_events_url\": \"https://api.github.com/users/baxterthehacker/received_events\",\n" +
                    "                    \"type\": \"User\",\n" +
                    "                    \"site_admin\": false\n" +
                    "                },\n" +
                    "                \"private\": false,\n" +
                    "                \"html_url\": \"https://github.com/baxterthehacker/public-repo\",\n" +
                    "                \"description\": \"\",\n" +
                    "                \"fork\": false,\n" +
                    "                \"url\": \"https://api.github.com/repos/baxterthehacker/public-repo\",\n" +
                    "                \"forks_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/forks\",\n" +
                    "                \"keys_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/keys{/key_id}\",\n" +
                    "                \"collaborators_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/collaborators{/collaborator}\",\n" +
                    "                \"teams_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/teams\",\n" +
                    "                \"hooks_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/hooks\",\n" +
                    "                \"issue_events_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues/events{/number}\",\n" +
                    "                \"events_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/events\",\n" +
                    "                \"assignees_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/assignees{/user}\",\n" +
                    "                \"branches_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/branches{/branch}\",\n" +
                    "                \"tags_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/tags\",\n" +
                    "                \"blobs_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/blobs{/sha}\",\n" +
                    "                \"git_tags_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/tags{/sha}\",\n" +
                    "                \"git_refs_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/refs{/sha}\",\n" +
                    "                \"trees_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/trees{/sha}\",\n" +
                    "                \"statuses_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/statuses/{sha}\",\n" +
                    "                \"languages_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/languages\",\n" +
                    "                \"stargazers_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/stargazers\",\n" +
                    "                \"contributors_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/contributors\",\n" +
                    "                \"subscribers_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/subscribers\",\n" +
                    "                \"subscription_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/subscription\",\n" +
                    "                \"commits_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/commits{/sha}\",\n" +
                    "                \"git_commits_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/git/commits{/sha}\",\n" +
                    "                \"comments_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/comments{/number}\",\n" +
                    "                \"issue_comment_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues/comments{/number}\",\n" +
                    "                \"contents_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/contents/{+path}\",\n" +
                    "                \"compare_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/compare/{base}...{head}\",\n" +
                    "                \"merges_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/merges\",\n" +
                    "                \"archive_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/{archive_format}{/ref}\",\n" +
                    "                \"downloads_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/downloads\",\n" +
                    "                \"issues_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/issues{/number}\",\n" +
                    "                \"pulls_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/pulls{/number}\",\n" +
                    "                \"milestones_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/milestones{/number}\",\n" +
                    "                \"notifications_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/notifications{?since,all,participating}\",\n" +
                    "                \"labels_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/labels{/name}\",\n" +
                    "                \"releases_url\": \"https://api.github.com/repos/baxterthehacker/public-repo/releases{/id}\",\n" +
                    "                \"created_at\": \"2015-05-05T23:40:12Z\",\n" +
                    "                \"updated_at\": \"2015-05-05T23:40:30Z\",\n" +
                    "                \"pushed_at\": \"2015-05-05T23:40:27Z\",\n" +
                    "                \"git_url\": \"git://github.com/baxterthehacker/public-repo.git\",\n" +
                    "                \"ssh_url\": \"git@github.com:baxterthehacker/public-repo.git\",\n" +
                    "                \"clone_url\": \"https://github.com/baxterthehacker/public-repo.git\",\n" +
                    "                \"svn_url\": \"https://github.com/baxterthehacker/public-repo\",\n" +
                    "                \"homepage\": null,\n" +
                    "                \"size\": 0,\n" +
                    "                \"stargazers_count\": 0,\n" +
                    "                \"watchers_count\": 0,\n" +
                    "                \"language\": null,\n" +
                    "                \"has_issues\": true,\n" +
                    "                \"has_downloads\": true,\n" +
                    "                \"has_wiki\": true,\n" +
                    "                \"has_pages\": true,\n" +
                    "                \"forks_count\": 0,\n" +
                    "                \"mirror_url\": null,\n" +
                    "                \"open_issues_count\": 2,\n" +
                    "                \"forks\": 0,\n" +
                    "                \"open_issues\": 2,\n" +
                    "                \"watchers\": 0,\n" +
                    "                \"default_branch\": \"master\"\n" +
                    "            },\n" +
                    "            \"sender\": {\n" +
                    "                \"login\": \"baxterthehacker\",\n" +
                    "                \"id\": 6752317,\n" +
                    "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/6752317?v=3\",\n" +
                    "                \"gravatar_id\": \"\",\n" +
                    "                \"url\": \"https://api.github.com/users/baxterthehacker\",\n" +
                    "                \"html_url\": \"https://github.com/baxterthehacker\",\n" +
                    "                \"followers_url\": \"https://api.github.com/users/baxterthehacker/followers\",\n" +
                    "                \"following_url\": \"https://api.github.com/users/baxterthehacker/following{/other_user}\",\n" +
                    "                \"gists_url\": \"https://api.github.com/users/baxterthehacker/gists{/gist_id}\",\n" +
                    "                \"starred_url\": \"https://api.github.com/users/baxterthehacker/starred{/owner}{/repo}\",\n" +
                    "                \"subscriptions_url\": \"https://api.github.com/users/baxterthehacker/subscriptions\",\n" +
                    "                \"organizations_url\": \"https://api.github.com/users/baxterthehacker/orgs\",\n" +
                    "                \"repos_url\": \"https://api.github.com/users/baxterthehacker/repos\",\n" +
                    "                \"events_url\": \"https://api.github.com/users/baxterthehacker/events{/privacy}\",\n" +
                    "                \"received_events_url\": \"https://api.github.com/users/baxterthehacker/received_events\",\n" +
                    "                \"type\": \"User\",\n" +
                    "                \"site_admin\": false\n" +
                    "            }\n" +
                    "        },\n" +
                    "        \"repo\": {\n" +
                    "            \"id\": 3,\n" +
                    "            \"name\": \"octocat/Hello-World\",\n" +
                    "            \"url\": \"https://api.github.com/repos/octocat/Hello-World\"\n" +
                    "        },\n" +
                    "        \"actor\": {\n" +
                    "            \"id\": 1,\n" +
                    "            \"login\": \"octocat\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
                    "            \"url\": \"https://api.github.com/users/octocat\"\n" +
                    "        },\n" +
                    "        \"org\": {\n" +
                    "            \"id\": 1,\n" +
                    "            \"login\": \"github\",\n" +
                    "            \"gravatar_id\": \"\",\n" +
                    "            \"url\": \"https://api.github.com/orgs/github\",\n" +
                    "            \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\"\n" +
                    "        },\n" +
                    "        \"created_at\": \"2011-09-06T17:26:27Z\",\n" +
                    "        \"id\": \"12345\"\n" +
                    "    }\n" +
                    "]";

}