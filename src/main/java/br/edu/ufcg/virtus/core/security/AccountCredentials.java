package br.edu.ufcg.virtus.core.security;

/**
 * Credentials of the Account.
 *
 * @author Virtus
 *
 */
public class AccountCredentials {

    /**
     * Username.
     */
    private String username;

    /**
     * Password.
     */
    private String password;

    /**
     * Gets the username.
     *
     * @return Username.
     */
    public String getUsername() {

        return this.username;
    }

    /**
     * Sets the username.
     *
     * @param username
     *            Username.
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * Gets the password.
     *
     * @return Password.
     */
    public String getPassword() {

        return this.password;
    }

    /**
     * Sets the password.
     *
     * @param password
     *            Password.
     */
    public void setPassword(String password) {

        this.password = password;
    }
}
