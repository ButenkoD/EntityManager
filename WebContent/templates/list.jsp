<%@page import="java.util.ArrayList"%>
<%@page import="com.andersenlab.entity_manager.entity.AbstractEntity"%>

<a href="index">Back</a>
</br>
<a href="${entityName}/create">Create</a>
</br>
List:
</br>
<%
ArrayList<AbstractEntity> list = (ArrayList<AbstractEntity>)request.getAttribute("list");
for (AbstractEntity entity: list) { %>
	<%= entity.toString() %>
	<jsp:include page="deleteForm.jsp">
		<jsp:param name="action" value="${entityName}/delete"/>
		<jsp:param name="id" value="<%= entity.getId() %>"/>
	</jsp:include>
<% } %>