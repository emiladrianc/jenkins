#!/usr/bin/env groovy
import groovy.json.JsonSlurper

def getAndroidBuildVersion(def appEnvironemnt, def project, def brand) {

    final HOCKEYAPP_STAGING_CARE_APP_ID_MAP = ['myElectrolux'       : 'cf116a138086435dbc04f1678b50cc6d',
                                               'myElectrolux-sprint': '3e1788b2e0c64d47ad85e37cbfb40918',
                                               'myAEG'              : '8c16928284044b15a50916831d69d66e']

    final HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP = ['taste-staging': 'ffe463f1bf804d1c9ce09efbe75909b2']

    final HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP = ['myelectrolux': 'd358888fe5c641eb853baaef40270ebe']


    def ACCESS_TOKEN_HOCYEKAPP_API_ALL_APPS = "f670dc1b1b05467ea48647bef0d31016"
    def HOCKEYAPP_APP_ID

    if (appEnvironment == "staging") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myElectrolux")
            } else if (brand == "myAEG") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_CARE_APP_ID_MAP.get("myAEG")
            }
        } else if (project == "kitchen") {
            if (brand == "taste") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_STAGING_KITCHEN_APP_ID_MAP.get("taste")
            }
        }
    } else if (appEnvironment == "preProduction") {
        if (project == "care") {
            if (brand == "myElectrolux") {
                HOCKEYAPP_APP_ID = HOCKEYAPP_PREPRODUCTION_CARE_APP_ID_MAP.get("myelectrolux-staging")
            }
        }
    }

    def cmd = ['bash', '-c', "curl -H 'X-HockeyAppToken:${ACCESS_TOKEN_HOCYEKAPP_API_ALL_APPS}' https://rink.hockeyapp.net/api/2/apps/3e1788b2e0c64d47ad85e37cbfb40918/app_versions.json"]
    def appVersionsJson = cmd.execute().text
    JsonSlurper jsonSlurper = new JsonSlurper()
    Object result = jsonSlurper.parseText(appVersionsJson)
    def shortBuildVersions = new ArrayList()
    result.app_versions.each { app ->
        shortBuildVersions.add(app.shortversion)
    }
    def firstTenBuildVersions = shortBuildVersions.subList(0, 9)
    return firstTenBuildVersionshockeyappHelper.join('\n')
}


