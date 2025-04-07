<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
    Map<String, String> user = (Map<String, String>) request.getAttribute("user");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 0;
            background-color: #f8f9fa;
        }

        /* Navigation Bar Styling */
        header {
            background-color: #1a1a1a;
            padding: 15px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: white;
        }

        .logo {
            font-size: 28px;
            font-weight: bold;
            color: white;
        }

        .menu {
            display: flex;
            gap: 30px;
        }

        .menu a {
            color: white;
            text-decoration: none;
            font-size: 16px;
        }

        .profile-container {
            display: flex;
            gap: 20px;
            align-items: center;
        }

        .profile-container .profile-pic {
            border-radius: 50%;
            width: 40px;
            height: 40px;
            background-color: #eeeeee;
            object-fit: cover;
        }

        /* Profile Page */
        .profile-page {
            padding: 40px 80px;
        }

        /* Title and Slogan Styling */
        .profile-header {
            text-align: left;
            margin-bottom: 30px;
        }

        .profile-header h2 {
            font-size: 32px;
            color: #333;
            margin: 0;
        }

        .profile-header p {
            font-size: 18px;
            color: #666;
            margin-top: 5px;
        }

        .profile-container {
            display: flex;
            gap: 40px;
            justify-content: space-between;
        }

        .card {
            background: white;
            border-radius: 10px;
            padding: 30px;
            flex: 1;
            box-shadow: 0 0 20px rgba(0,0,0,0.05);
        }

        .card h3 {
            margin-top: 0;
            font-size: 22px;
            color: #333;
        }

        .info-label {
            font-size: 14px;
            color: #888;
        }

        .info-value {
            font-size: 16px;
            font-weight: bold;
            margin-bottom: 15px;
        }

        .avatar {
            width: 100px;
            height: 100px;
            border-radius: 50%;
            background: #eee;
            margin-bottom: 10px;
        }

        .button {
            background-color: #6f42c1;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            display: inline-block;
            margin-top: 10px;
        }

        .language-selector-card {
            background: white;
            border-radius: 10px;
            padding: 20px;
            box-shadow: 0 0 20px rgba(0,0,0,0.05);
            width: 300px;
            margin-top: 30px;
        }

        .language-selector-card h3 {
            margin-top: 0;
            font-size: 22px;
            color: #333;
        }

        .language-selector-card p {
            font-size: 16px;
            font-weight: bold;
        }

        .language-selector-card .button {
            background-color: #6f42c1;
            padding: 8px 15px;
            font-size: 16px;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<!-- Navigation Bar -->
<header>
    <div class="logo">LOGO</div>
    <div class="menu">
        <a href="#">Markets</a>
        <a href="#">My Portfolio</a>
        <a href="#">Compare Stocks</a>
    </div>
    <div class="profile-container">
        <a href="profile">
            <!-- Profile image on the top bar -->
            <img class="profile-pic" src="https://images.unsplash.com/photo-1740252117070-7aa2955b25f8?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="User Avatar"/>
        </a>
        <a href="#" class="button">Profile</a>
    </div>
</header>

<!-- Profile Page Content -->
<div class="profile-page">
    <!-- Title and Slogan -->
    <div class="profile-header">
        <h2>Personal Info</h2>
        <p>Here's all the details we've got for you</p>
    </div>

    <div class="profile-container">
        <div class="card">
            <!-- Profile image in the profile section -->
            <img class="avatar" src="https://images.unsplash.com/photo-1740252117070-7aa2955b25f8?q=80&w=2080&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D" alt="User Avatar"/>
            <h3><%= user.get("firstName") %> <%= user.get("lastName") %></h3>
            <p class="info-value"><%= user.get("email") %></p>
            <a href="#" class="button">Update profile photo</a>
        </div>

        <div class="card">
            <h3>Your details</h3>
            <p class="info-label">First name</p>
            <p class="info-value"><%= user.get("firstName") %></p>

            <p class="info-label">Surname</p>
            <p class="info-value"><%= user.get("lastName") %></p>

            <p class="info-label">Email</p>
            <p class="info-value"><%= user.get("email") %></p>

            <p class="info-label">Session Active</p>
            <p class="info-value"><%= user.get("loginStatus").equals("true") ? "Yes" : "No" %></p>

            <a href="#" class="button">Update personal details</a>
        </div>
    </div>

    <!-- Language Display and Update Button -->
    <div class="language-selector-card">
        <h3>Language</h3>
        <p>English - United Kingdom</p> <!-- Display selected language -->
        <a href="#" class="button">Update Language</a> <!-- Button to change language -->
    </div>

</div>

</body>
</html>


