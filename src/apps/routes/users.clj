(ns apps.routes.users
  (:use [common-swagger-api.schema]
        [apps.routes.domain.user]
        [apps.routes.params]
        [apps.user :only [current-user]]
        [ring.util.http-response :only [ok]])
  (:require [apps.service.users :as users]))

(defroutes* users
  (POST* "/by-id" []
         :query [params SecuredQueryParams]
         :body [body (describe UserIds "The list of user IDs to look up.")]
         :return Users
         :summary "Look up usernames"
         :description "This endpoint returns usernames for internal user IDs."
         (ok (users/by-id body)))

  (GET* "/authenticated" []
        :query [params SecuredQueryParams]
        :return User
        :summary "Get the Authenticated User"
        :description "This endpoint returns information about the authenticated user."
        (ok (users/authenticated current-user)))

  (POST* "/login" []
         :query [params LoginParams]
         :return LoginResponse
         :summary "Record a User Login"
         :description "Terrain calls this service to record when a user logs in."
         (ok (users/login current-user params)))

  (POST* "/logout" []
         :query [params LogoutParams]
         :summary "Record a User Logout"
         :description "Terrain calls this service to record when a user logs out."
         (ok (users/logout current-user params))))
