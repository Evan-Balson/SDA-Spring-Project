<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', sans-serif;
            background: url('https://images.unsplash.com/photo-1486406146926-c627a92ad1ab?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D') no-repeat center center fixed;
            background-size: cover;
            color: white;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }

        .container {
            background-color: rgba(0, 0, 0, 0.6);
            padding: 40px;
            border-radius: 20px;
            text-align: center;
            width: 400px;
        }

        h1 {
            margin-bottom: 30px;
            font-size: 36px;
        }

        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 15px;
            margin: 10px 0;
            border: none;
            border-radius: 12px;
            font-size: 16px;
            text-align: center;
        }

        .register-btn {
            margin-top: 20px;
            background-color: #264D1C;
            color: white;
            padding: 15px 30px;
            font-size: 18px;
            border: none;
            border-radius: 40px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .register-btn:hover {
            background-color: #1d3914;
        }

        .arrow {
            margin-left: 10px;
            font-size: 18px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Register with StockShark</h1>
    <form action="register" method="post">
        <input type="text" name="username" placeholder="Email" required>
        <input type="password" name="password" placeholder="Password" required>
        <input type="text" name="firstName" placeholder="First Name" required> <!-- New field -->
        <input type="text" name="lastName" placeholder="Last Name" required> <!-- New field -->
        <button type="submit" class="register-btn">
            Register <span class="arrow">→</span>
        </button>
    </form>
</div>
</body>
</html>

