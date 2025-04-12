<html>
<head>
    <title>User Profile</title>
    <link rel="stylesheet" type="text/css" href="css/profile.css" />
</head>
<body>
<%@ include file="header.jsp" %>

<main>

    <!-- Account Settings Card: for email/password updates and account deletion -->
    <section class="account-settings">
        <div class="settings-card">
            <h3>Account Settings</h3>

            <!-- Update Email Form -->
            <form action="${pageContext.request.contextPath}/Profile" method="post" class="update-form">
                <h4>Update Email</h4>
                <label for="email">New Email:</label>
                <input type="email" id="email" name="email" placeholder="newemail@example.com" required />
                <button type="submit" name="action" value="updateEmail">Update Email</button>
            </form>

            <!-- Update Password Form -->
            <form action="${pageContext.request.contextPath}/Profile" method="post" class="update-form">
                <h4>Update Password</h4>
                <label for="currentPassword">Current Password:</label>
                <input type="password" id="currentPassword" name="currentPassword" required />

                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword" required />
                <button type="submit" name="action" value="updatePassword">Update Password</button>
            </form>

            <!-- Delete Account Form -->
            <form action="${pageContext.request.contextPath}/Profile" method="post" class="delete-form">
                <h4>Delete Account</h4>
                <p>Warning: This action cannot be undone.</p>
                <button type="submit" name="action" value="deleteAccount" class="delete-button">Delete Account</button>
            </form>
        </div>
    </section>
</main>

<%@ include file="footer.jsp" %>
</body>
</html>
