# Keycloak Auth Service

This GitHub repository contains a Java Spring application for integrating with Keycloak for authentication and token management. It provides a RESTful API for creating and refreshing access tokens. The main components of the repository are:

- **TokenController**: A REST controller handling token creation and refresh requests.

- **KeycloakService**: A service that communicates with Keycloak to create and refresh access tokens. It uses Keycloak's Java client library for this purpose.

The application is designed for easy integration into other projects that require Keycloak-based authentication. It can be configured with your Keycloak server details and provides simple endpoints for token management.
