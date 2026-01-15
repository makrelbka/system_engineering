<template>
  <router-view />
</template>

<script setup>
import { ref, provide } from 'vue'

// Создаем глобальную корзину, доступную на всех страницах
const cart = ref([])

// Предоставляем корзину для доступа из всех компонентов
provide('cart', cart)
</script>
