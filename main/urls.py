from django.urls import path, include
from .views import home, URLAdder, URLModifier, URLDelete

urlpatterns = [
    path('',home,name='home'),
    path('api/add/', URLAdder.as_view()),
    path('api/modify/',URLModifier.as_view()),
    path('api/delete/<str:pk>/',URLDelete.as_view())
]
