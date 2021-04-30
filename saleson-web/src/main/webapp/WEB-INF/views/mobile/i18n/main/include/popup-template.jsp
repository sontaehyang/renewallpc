<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="op" 	uri="/WEB-INF/tlds/functions" %>


<script type="text/html" id="popupTemplate">
	<div id="popup_{{popupId}}" style="{{popupStyle}}" class="m-admin-popup">
		<div handlefor="popup_{{popupId}}" onMousedown="initializedrag(event, '{{popupId}}')" onMouseover="dragswitch=1;if (ns4) drag_dropns(popup_{{popupId}})" onMouseout="dragswitch=0" style="{{handleStyle}}" id="dragbar"></div>
		<div class="content" style="{{contentStyle}}">
			<div class="popLink">
				<table width="{{width}}" height="{{height}}" border="0" cellpadding="0" cellspacing="0">
					<tr height="*">
						<td valign="top">
							{{popupContent}}
						</td>
					</tr>
					<tr>
					
						<td align="left" style="padding:10px 0 0 0px;margin:0px;line-height:0px;">
							<table width="100%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td width="20" style="padding:0px 5px 0 0; margin:0px;line-height:0px;"><input type="checkbox" name="chkNotice" id="chkNotice_{{popupId}}" value="" onClick="closeWin('{{popupId}}')" class="radio" style="cursor:hand;margin-bottom:2px;_margin-bottom:4px; width:18px; height:18px;" /></td>
									<td><label style="cursor:hand; font-size:14px;padding:0px 5px 0 0; " for="chkNotice_{{popupId}}">${op:message('M01475')}</label> <a href="javascript:popClose('{{popupId}}');"><img src="/content/images/btn/btn_close2.gif" style="cursor:hand" /></a></td>
								</tr>
							</table>
						</td>
				
					</tr>
				</table>
			</div>
		</div>
	</div>
</script>
