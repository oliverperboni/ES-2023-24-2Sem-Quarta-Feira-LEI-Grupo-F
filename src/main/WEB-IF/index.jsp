<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabela de Horarios</title>
</head>
<body>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <% ReadCSV data = new ReadCSV()>

    <table>
        <tr>
            <td>Curso</td>
            <td>Unidade Curricular</td>
            <td>Turno</td>
            <td>Turma</td>
            <td>Inscritos no turno</td>
            <td> Dia da semana </td>
            <td> Hora início da aula </td>
            <td> Hora fim da aula </td>
            <td> Data da aula </td>
            <td> Características da sala pedida para aula </td>
            <td> Sala atribuida à aula </td>
        </tr>
        <c:forEach var="data" items="${line}">
            <tr>
                <td><c:out value="${line.getCurso}" /></td>
                <td><c:out value="${line.getUnidadeCurricular}" /></td>
                <td><c:out value="${line.getTurno}" /></td>
                <td><c:out value="${line.getTurma}" /></td>
                <td><c:out value="${line.getInscritos}" /></td>
                <td><c:out value="${line.getDiaSemana}" /></td>
                <td><c:out value="${line.getHoraInicio}" /></td>
                <td><c:out value="${line.getHoraFim}" /></td>
                <td><c:out value="${line.getDataAula}" /></td>
                <td><c:out value="${line.getCaracteristicasSala}" /></td>
                <td><c:out value="${line.getSala}" /></td>
            </tr>
        </c:forEach>


    </table>

    
</body>
</html>