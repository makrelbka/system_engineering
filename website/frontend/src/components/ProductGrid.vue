<template>
  <section class="container mx-auto px-4 py-16">
    <div class="mb-12 text-center">
      <h2 class="mb-4 text-4xl">Наши букеты</h2>
      <p class="text-lg text-gray-600">
        Выберите готовый букет или закажите индивидуальный
      </p>
    </div>
    <div class="grid grid-cols-1 gap-6 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
      <ProductCard
        v-for="product in products"
        :key="product.id"
        :product="product"
        @add-to-cart="$emit('add-to-cart', product)"
      />
    </div>
  </section>
</template>

<script setup>
import ProductCard from './ProductCard.vue'

defineProps({
  products: {
    type: Array,
    required: true
  }
})

defineEmits(['add-to-cart'])
</script>
