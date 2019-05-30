#!/usr/bin/env groovy

def getBuildVersion(def project, def brand) {
    
    final HOCKEYAPP_CARE_APP_ID_MAP = ['myelectrolux-sprint' : 'cf116a138086435dbc04f1678b50cc6d',
                                  'myelectrolux-staging' : '3e1788b2e0c64d47ad85e37cbfb40918',
                                  'myaeg-staging' : '8c16928284044b15a50916831d69d66e']

    final HOCKEYAPP_KITCHEN_APP_ID_MAP = ['taste-staging' : 'ffe463f1bf804d1c9ce09efbe75909b2']

    def ACCESS_TOKEN_HOCYEKAPP_API_ALL_APPS = f670dc1b1b05467ea48647bef0d31016
    def HOCKEYAPP_APP_ID

    if (project == "care"){
        if (brand == "myelectrolux-staging"){
        HOCKEYAPP_APP_ID = HOCKEYAPP_CARE_APP_ID_MAP.get("myelectrolux-staging")}
        else if (brand == "myaeg-staging"){
            HOCKEYAPP_APP_ID = HOCKEYAPP_CARE_APP_ID_MAP.get("myaeg-staging")
        }
    }
    else if(project == "kitchen"){
        if (brand == "taste-staging"){
            HOCKEYAPP_APP_ID = HOCKEYAPP_KITCHEN_APP_ID_MAP.get("taste-staging")
        }
    }

    def BUILD_LIST = [ 'bash', '-c', "curl -H 'X-HockeyAppToken:${ACCESS_TOKEN_HOCYEKAPP_API_ALL_APPS}' https://rink.hockeyapp.net/api/2/apps/${HOCKEYAPP_APP_ID}/app_versions.json | jq -r '[.app_versions[:5][].shortversion] | join(\", \")'"].execute().text
  
    return BUILD_LIST
}
