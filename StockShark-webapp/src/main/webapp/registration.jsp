<%@ include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/register.css" />
<main>
    <section class="registration-section">
        <h2>Register</h2>
        <form action="Registration" method="post">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required />
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />
            </div>
            <div class="form-group">
                <label for="confirmPassword">Confirm Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required />
            </div>
            <button type="submit">Register</button>
        </form>
    </section>
</main>
<%@ include file="footer.jsp" %>
