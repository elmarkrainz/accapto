	<TextView
		android:layout_width="wrap_content"
		android:layout_height="wrap_content"
		android:text="${name}"
		android:labelFor="@+id/txt${name_nospace}"
		android:importantForAccessibility="2"/>
		
	<EditText
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:id="@+id/txt${name_nospace}"
		android:hint="${description}"
		android:minHeight="48dp"
		android:minWidth="48dp"	/>
			