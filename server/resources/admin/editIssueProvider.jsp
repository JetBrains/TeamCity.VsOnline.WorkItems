<%@ include file="/include.jsp"%>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<script type="text/javascript">
  BS.VsOnlineActions = {
    prefix: "https://",
    suffix: ".visualstudio.com",

    onInit: function() {
      var collection =  $j('#collection').val();
      if (collection === '') {
        $j('#collection').val('DefaultCollection');
      }
      var pattern = $j('#pattern').val();
      if (pattern === '') {
        $j('#pattern').val('#(\\d+)');
      }
      this.onAccountUpdate();
    },

    onAccountUpdate: function() {
      $j('#host').val(this.prefix + $j('#account').val() + this.suffix);
    }
  }
</script>

<div>
  <table class="editProviderTable">
    <c:if test="${showType}">
      <tr>
        <th><label class="shortLabel">Connection Type:</label></th>
        <td>Visual Studio Online</td>
      </tr>
    </c:if>
    <tr>
      <th><label for="name" class="shortLabel">Display Name: <l:star/></label></th>
      <td>
        <props:textProperty name="name" maxlength="100"/>
        <span id="error_name" class="error"></span>
      </td>
    </tr>
    <props:hiddenProperty name="host"/>
    <tr>
      <th><label for="account" class="shortLabel">Account Name: <l:star/></label></th>
      <td>
        <props:textProperty name="account" maxlength="50" onchange="BS.VsOnlineActions.onAccountUpdate();"/>
        <span class="fieldExplanation" id="explanation_account">{account name}.visualstudio.com</span>
        <span id="error_account" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="username" class="shortLabel">Username: <l:star/></label></th>
      <td>
        <props:textProperty name="username" maxlength="100"/>
        <span id="error_username" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="secure:password" class="shortLabel">Password: <l:star/></label></th>
      <td>
        <props:passwordProperty name="secure:password" maxlength="100"/>
        <span class="fieldExplanation" id="explanation_secure:password">
          <a href="http://www.visualstudio.com/en-us/integrate/get-started/get-started-auth-introduction-vsi">Alternate Credentials</a>
          must be enabled for used account
        </span>
        <span id="error_secure:password" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="collection" class="shortLabel">Collection:</label></th>
      <td>
        <props:textProperty name="collection" maxlength="100"/>
        <span id="error_collection" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="project" class="shortLabel">Project:<l:star/></label></th>
      <td>
        <props:textProperty name="project" maxlength="100"/>
        <span id="error_project" class="error"></span>
      </td>
    </tr>
    <tr>
      <th><label for="pattern" class="shortLabel">Pattern: <l:star/></label></th>
      <td>
        <props:textProperty name="pattern" maxlength="100" style="width: 16em;"/>
        <span id="error_pattern" class="error"></span>
        <span class="fieldExplanation">Use general regexp, e.g. #(\d+)<bs:help file="Issue+Tracker+Tab"/></span>
      </td>
    </tr>
  </table>
</div>

<script type="text/javascript">
  BS.VsOnlineActions.onInit();
</script>
