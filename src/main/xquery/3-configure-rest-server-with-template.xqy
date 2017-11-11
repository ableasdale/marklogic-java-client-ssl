xquery version "1.0-ml";

(: Configuration Script 3: Configure ReST Endpoint for SSL :)

import module namespace admin = "http://marklogic.com/xdmp/admin" at "/MarkLogic/admin.xqy";
import module namespace pki = "http://marklogic.com/xdmp/pki" at "/MarkLogic/pki.xqy";

declare variable $GROUP := "Default";
declare variable $CONFIG := admin:get-configuration();
declare variable $TEMPLATE-NAME := "test-template";

let $CONFIG := admin:appserver-set-ssl-certificate-template(
    $CONFIG,
    admin:appserver-get-id($CONFIG, admin:group-get-id($CONFIG, $GROUP), "RESTstop"),
    pki:template-get-id(pki:get-template-by-name($TEMPLATE-NAME)))

return admin:save-configuration($CONFIG)
(: FIXME - APP SERVER NAME IS HARDCODED! :)