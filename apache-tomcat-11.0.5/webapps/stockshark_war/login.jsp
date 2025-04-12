<%@ include file="header.jsp" %>
<link rel="stylesheet" type="text/css" href="css/login.css" />
<main>
    <section class="login-section">
        <h2>Login</h2>
        <form action="${pageContext.request.contextPath}/Login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" required />
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required />
            </div>
            <button type="submit">Login</button>
        </form>
        <p>Don't have an account? <a href="${pageContext.request.contextPath}/Registration">Register here</a>.</p>
    </section>
</main>
<%@ include file="footer.jsp" %>
