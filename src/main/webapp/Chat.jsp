<%-- 
    Document   : market.jsp
    Created on : Oct 4, 2023, 10:27:35 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags always come first -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <!-- Bootstrap CSS -->
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 

        <title>Forum</title>
        <!-- Link Iconn  -->
        <link rel="stylesheet" href="fontawesome-free-6.4.2-web/css/fontawesome.css"> 
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

        <!-- jQuery first, then Popper.js, then Bootstrap JS. -->

        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>

        <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.10.2/dist/umd/popper.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>


        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


        <!--  -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"></script>

        <!-- Link CSS -->

        <link rel="stylesheet" href="assets/css/marketv2.css">
        <link rel="stylesheet" href="assets/css/standar-style.css">
        <link rel="stylesheet" href="assets/css/chat-style.css">


    </head>

    <body>
        <c:set var="ListOfConversation" value="${sessionScope.ListOfConversation}"/>
        <c:set var="ListOfProductInConversation" value="${sessionScope.ListOfProductInConversation}"/>
        <c:set var="ListOfMessage" value="${requestScope.ListOfMessage}"/>
        <c:set var="Owner" value="${sessionScope.USER_NAME}"/>
        <jsp:include page="header.jsp" />
        <div class="menu d-flex justify-content-center " style="margin: 90px 0 0 50px;">

            <div class="product col-sm-7 " >
                <div class="row container p-0">
                    <div class="listConversation col-sm-4 " style="background-color: #A3B18A; height: 500px; overflow: scroll;" >
                        <ul class="list-group">
                            <c:forEach items="${ListOfConversation}" var="conversation">
                                <c:forEach items="${ListOfProductInConversation}" var="Product">
                                    <c:if test="${Product.productId == conversation.product_id }">
                                        <li id="conver_${conversation.conversation_id}" class="Convert-li list-group-item border-0 m-1 " 
                                            style="background-color: #A3B18A">
                                            <a class="text-white Conversation-name"
                                               onclick="loadMessages(${conversation.conversation_id});">
                                                ${Product.title}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="listMessage col-sm-8 pr-0 " style=" height: 500px;background-color: #DAD7CD; " >
                        <!-- Message list content goes here -->

                        <div id="messageContainer" style="height: 440px; overflow:scroll;">


                        </div>
                        <form id="message_input">
                            <div class="row mt-3">
                                <div class="col-8">
                                    <input type="text" class="form-control" placeholder="Type your message">
                                </div>
                                <div class="col-4">
                                    <button class="btn btn-primary">Send</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
            <script>
                let stateConver = null;
                function loadMessages(conversationID) {
                    stateConver = conversationID;
                    const liElements = document.getElementsByClassName("Convert-li");
                    // Lặp qua tất cả các phần tử <li> có lớp `Convert-li`
                    for (let i = 0; i < liElements.length; i++) {
                        const liElement = liElements[i];
                        // Kiểm tra id của phần tử
                        liElement.id !== "conver_" + conversationID ? liElement.classList.remove('active') : liElement.classList.add('active');

                    }
                    const fetchAndRenderMessages = () => {
                        fetch('getMessageInConversation?conversationID=' + conversationID)
                                .then(response => response.json())
                                .then(data => {
                                    renderMessages(data);
                                });
                    };
                    fetchAndRenderMessages();
                    setInterval(fetchAndRenderMessages, 1000);

                }

                function renderMessages(messages) {
                    var messageContainer = document.getElementById('messageContainer');
                    messageContainer.innerHTML = '';
                    if (messages.length === 0) {
                        messageContainer.innerHTML = `
                               <div class="chat-message d-flex justify-content-center align-items-center" style=" height: 400px;">
                                   <p>Chat để kết nối - Cùng nhau làm nên giao dịch tốt nhất!</p>
                               </div>`;
                    } else {
                        var messageDivs = messages.map(function (message) {
                            if (message.user_id === ${Owner.user_ID}) {
                                console.log(message.messages_content)
                                messageDiv = `
                                       <div class="col-6 offset-6 pt-2">
                                          <div class="my-message bg-info text-white pt-2 pb-2 pl-3 pr-3">`
                                        + message.messages_content +
                                        `</div>
                                      </div>`;
                            } else {
                                messageDiv = `
                                          <div class="col-6 pt-2">
                                              <div class="their-message bg-secondary text-white pt-2 pb-2 pl-3 pr-3">`
                                        + message.messages_content +
                                        `</div>
                                          </div>
                                          <div class="col-6 pt-2">
                                          </div>
                                           `;
                            }
                            return messageDiv;
                        });
                        messageContainer.innerHTML = messageDivs.join('');
                        messageContainer.scrollTop = messageContainer.scrollHeight;
                    }
                }

                const form = document.getElementById('message_input');
                const input = form.querySelector('input');
                const button = form.querySelector('button');
                button.addEventListener('click', sendMessage);
                function sendMessage(event) {
                    event.preventDefault();
                    const message = input.value;
                    if (message.trim() === '') {
                        // Message is empty, do not send
                        return;
                    }
                    console.log(message)
                    const conversationID = stateConver;
                    console.log(conversationID)
                    // Send a POST request using Fetch API
                    fetch('createMessage?message=' + message + '&conversationID=' + conversationID)
                            .then(response => response.json())
                            .then(result => {
                                console.log(result);
                            })
                            .catch(error => {
                                console.error('Error:', error);
                            });
                    input.value = '';
                }
            </script>
    </body>
</html>
